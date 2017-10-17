package com.xiangshangban.transit_service.bean;

/**
 * Created by liuguanglong on 2017/8/17.
 */

//请假信息表
public class LeaveInfo {

    private String proposerId;          //请假人ID（当前登录用户的ID）
    private String proposerName;        //请假人姓名
    private String processInstanceId;   //流程实例ID
    private String firstAssigneeId;     //一级审批人ID（字符串拼接多个审批人）
    private String secondAssigneeId;    //二级审批人ID
    private String thirdAssigneeId;     //三级审批人ID
    private String fourthAssigneeId;    //四级审批人ID
    private String fifthAssigneeId;     //五级审批人ID
    private String startTime;           //请假起始时间
    private String endTime;             //请假结束时间
    private String effectiveTimeLength; //请假有效时长
    private String unit;                //请假的计算单位
    private String judge;               //审批级数
    private String reason;              //请假原因
    private String applicationStatus;   //申请状态，0：待处理，1：处理中，2：已通过，3：已驳回，4：已撤回，5：已抛弃
    private String leaveType;           //请假类型，0：事假，1：年假，2：病假，3：长病假，4：调休，5：产假：，6：产检假，7：陪产假，8：哺乳假，9：婚假，10：丧假，11：探亲假
    private String applicationType;     //申请类型，0：加班，1：请假，2：出差，3：外出，4：补勤
    private String createTime;          //流程创建时间

    public String getProposerId() {
        return proposerId;
    }

    public void setProposerId(String proposerId) {
        this.proposerId = proposerId;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getFirstAssigneeId() {
        return firstAssigneeId;
    }

    public void setFirstAssigneeId(String firstAssigneeId) {
        this.firstAssigneeId = firstAssigneeId;
    }

    public String getSecondAssigneeId() {
        return secondAssigneeId;
    }

    public void setSecondAssigneeId(String secondAssigneeId) {
        this.secondAssigneeId = secondAssigneeId;
    }

    public String getThirdAssigneeId() {
        return thirdAssigneeId;
    }

    public void setThirdAssigneeId(String thirdAssigneeId) {
        this.thirdAssigneeId = thirdAssigneeId;
    }

    public String getFourthAssigneeId() {
        return fourthAssigneeId;
    }

    public void setFourthAssigneeId(String fourthAssigneeId) {
        this.fourthAssigneeId = fourthAssigneeId;
    }

    public String getFifthAssigneeId() {
        return fifthAssigneeId;
    }

    public void setFifthAssigneeId(String fifthAssigneeId) {
        this.fifthAssigneeId = fifthAssigneeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEffectiveTimeLength() {
        return effectiveTimeLength;
    }

    public void setEffectiveTimeLength(String effectiveTimeLength) {
        this.effectiveTimeLength = effectiveTimeLength;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
