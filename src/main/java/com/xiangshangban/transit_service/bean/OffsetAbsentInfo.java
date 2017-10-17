package com.xiangshangban.transit_service.bean;

/**
 * Created by liuguanglong on 2017/8/28.
 */

public class OffsetAbsentInfo {

    private String proposerId;          //补勤人ID（当前登录用户的ID）
    private String proposerName;        //补勤人姓名
    private String processInstanceId;   //流程实例ID
    private String firstAssigneeId;     //一级审批人ID（字符串拼接多个审批人）
    private String secondAssigneeId;    //二级审批人ID
    private String thirdAssigneeId;     //三级审批人ID
    private String fourthAssigneeId;    //四级审批人ID
    private String fifthAssigneeId;     //五级审批人ID
    private String offsetTime;          //补勤时间，操作类型为补卡时的时间参数，补假是起始时间和结束时间
    private String startTime;           //补勤起始时间
    private String endTime;             //补勤结束时间
    private String judge;               //审批级数
    private String reason;              //补勤原因
    private String applicationStatus;   //申请状态，0：待处理，1：处理中，2：已通过，3：已驳回，4：已撤回，5：已抛弃
    private String applicationType;     //申请类型，0：加班，1：请假，2：出差，3：外出，4：补勤
    private String absentType;          //缺勤类型
    private String operationType;       //补勤的操作类型，0：补卡，1：补假
    private String offsetVacationType;  //补假类型，补勤操作为补假时选择的补假类型，0：事假，1：年假，2：病假，3：长病假，4：调休，5：产假：，6：产检假，7：陪产假，8：哺乳假，9：婚假，10：丧假，11：探亲假
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

    public String getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(String offsetTime) {
        this.offsetTime = offsetTime;
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

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getAbsentType() {
        return absentType;
    }

    public void setAbsentType(String absentType) {
        this.absentType = absentType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOffsetVacationType() {
        return offsetVacationType;
    }

    public void setOffsetVacationType(String offsetVacationType) {
        this.offsetVacationType = offsetVacationType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
