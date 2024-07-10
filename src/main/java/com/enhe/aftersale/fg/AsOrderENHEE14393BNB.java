package com.enhe.aftersale.fg;

import com.enhe.aftersale.CarbonEnumKeyValue;
import com.enhe.aftersale.CarbonRelationType;
import com.enhe.aftersale.item.AsOrderENHEE14393Item;
import com.enhe.aftersale.pojo.AsKnowledge;
import com.kuangkie.carbon.fg.FirstRoundImproveFuncGroup;
import com.kuangkie.carbon.fg.FuncGroupContext;
import com.kuangkie.carbon.fg.ImproveResultFactory;
import com.kuangkie.carbon.fg.SecondRoundImproveFuncGroup;
import com.kuangkie.carbon.fg.ops.FGOpsComplexus;
import com.kuangkie.carbon.fg.ops.ProRecordOps;
import com.kuangkie.carbon.fg.ops.ProRecordOpsBuilder;
import com.kuangkie.carbon.record.FGRecordComplexus;
import com.kuangkie.carbon.record.ProRecord;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository(value = "enhee14393353376341314019334")
public class AsOrderENHEE14393BNB implements SecondRoundImproveFuncGroup, FirstRoundImproveFuncGroup {

	@Override
	public void secondImprove(FuncGroupContext context, ProRecord proRecord, FGRecordComplexus recordComplexus,
			ImproveResultFactory improveResultFactory) {
		//不存在发起人，当前用户为发起人
		if(!proRecord.contains(CarbonRelationType.RR_工单_发起人_系统用户)){
			improveResultFactory.getCurrentProRecordOpsBuilder().addRelation(CarbonRelationType.RR_工单_发起人_系统用户,context.getUserCode());
		}

		//判断是否创建知识库
		if(CarbonEnumKeyValue.工单命令_创建知识库_cjzsk==proRecord.getLongValue(AsOrderENHEE14393Item.基本属性组_工单命令)){
			AsKnowledge asKnowledge=new AsKnowledge();
			asKnowledge.setWtms问题描述(proRecord.getString(AsOrderENHEE14393Item.基本属性组_问题描述));
			asKnowledge.setJjfa解决方案(proRecord.getString(AsOrderENHEE14393Item.基本属性组_问题反馈));
			improveResultFactory.putRecordBean(asKnowledge);
			improveResultFactory.getCurrentProRecordOpsBuilder().putAttribute(AsOrderENHEE14393Item.基本属性组_已创建知识库,true);
			improveResultFactory.getCurrentProRecordOpsBuilder().addRelation(CarbonRelationType.RR_工单_关联知识库_知识库,asKnowledge.getRecordCode());
		}

	}

	@Override
	public void preImprove(FuncGroupContext context, ProRecord proRecord, ProRecordOps proRecordOps, FGRecordComplexus recordComplexus, FGOpsComplexus opsComplexus, ImproveResultFactory improveResultFactory) {
		long preState=proRecord.getLongValue(AsOrderENHEE14393Item.基本属性组_状态);
		long newState=proRecordOps.getLongValue(AsOrderENHEE14393Item.基本属性组_状态);
		if( newState>0){
			//工单提交时，记录提交时间
			if((CarbonEnumKeyValue.工单状态_新建_xj== preState && newState== CarbonEnumKeyValue.工单状态_待分配_dfp)
			|| ( preState<=0 && newState== CarbonEnumKeyValue.工单状态_待分配_dfp)){
				improveResultFactory.getCurrentProRecordOpsBuilder().putAttribute(AsOrderENHEE14393Item.基本属性组_提交时间,new Date());
			}
			//工单分配时记录分配时间
			if(CarbonEnumKeyValue.工单状态_待分配_dfp== preState && newState== CarbonEnumKeyValue.工单状态_处理中_clz){
				improveResultFactory.getCurrentProRecordOpsBuilder().putAttribute(AsOrderENHEE14393Item.基本属性组_开始处理时间,new Date());
			}
			if(CarbonEnumKeyValue.工单状态_处理中_clz== preState && newState== CarbonEnumKeyValue.工单状态_待审核_dsh){
				improveResultFactory.getCurrentProRecordOpsBuilder().putAttribute(AsOrderENHEE14393Item.基本属性组_完成处理时间,new Date());
			}
			//工单审核通过时记录工单审核人
			if(CarbonEnumKeyValue.工单状态_待审核_dsh== preState ){
				if(newState== CarbonEnumKeyValue.工单状态_完成_wc){
					//improveResultFactory.getCurrentProRecordOpsBuilder().putAttribute(AsOrderENHEE14393Item.基本属性组_完成处理时间,new Date());
					ProRecordOpsBuilder builder = improveResultFactory.getCurrentProRecordOpsBuilder();
					builder.addRelation(CarbonRelationType.RR_工单_审核人_系统用户,context.getUserCode());
					//工单审核完成时,并计算处理用时和总用时
					//计算处理耗时
//					proRecord.getLongValue(AsOrderENHEE14393Item.基本属性组_提交时间);
//					proRecord.getLongValue(AsOrderENHEE14393Item.基本属性组_开始处理时间);
//					proRecord.getLongValue(AsOrderENHEE14393Item.基本属性组_完成处理时间);
					builder.putAttribute(AsOrderENHEE14393Item.基本属性组_处理耗时,getDateTimeDiff(proRecord.getLongValue(AsOrderENHEE14393Item.基本属性组_开始处理时间),proRecord.getLongValue(AsOrderENHEE14393Item.基本属性组_完成处理时间)));
					builder.putAttribute(AsOrderENHEE14393Item.基本属性组_总耗时,getDateTimeDiff(proRecord.getLongValue(AsOrderENHEE14393Item.基本属性组_提交时间) ,proRecord.getLongValue(AsOrderENHEE14393Item.基本属性组_完成处理时间)));
				}else if(newState== CarbonEnumKeyValue.工单状态_处理中_clz){
					improveResultFactory.getCurrentProRecordOpsBuilder().addRelation(CarbonRelationType.RR_工单_审核人_系统用户,context.getUserCode());
				}
			}
		}
	}

	//计算两个时间差
	private Long getDateTimeDiff(long start ,long end){
		if(start>0 && end>0 && start<end){
			return (end-start)/1000;
		}
		return null;
	}

	@Override
	public void improve(FuncGroupContext context, ProRecord proRecord, FGRecordComplexus recordComplexus, ImproveResultFactory improveResultFactory) {

	}

	@Override
	public void postImprove(FuncGroupContext context, ProRecord proRecord, FGRecordComplexus recordComplexus, ImproveResultFactory improveResultFactory) {

	}
}
