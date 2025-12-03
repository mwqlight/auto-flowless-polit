import request from '../../utils/request';

/**
 * 创建智能体流程
 *
 * @param data
 */
export function addAgentFlow(data: any) {
  return request({
    url: '/agent-process/create',
    method: 'post',
    data: data
  });
}

/**
 * 获取智能体流程详细信息
 *
 * @param data
 */
export function getAgentFlowDetail(flowId: string) {
  return request({
    url: '/agent-process/getDetail?flowId=' + flowId,
    method: 'get'
  });
}

/**
 * 停用智能体流程
 *
 * @param data
 */
export function disableAgentFlow(flowId: string) {
  return request({
    url: '/agent-process/update/' + flowId + '?type=stop',
    method: 'put'
  });
}

/**
 * 根据智能体流程唯一标识查询流程列表
 *
 * @param uniqueId
 */
export function getAgentFlowListByUniqueId(uniqueId: string) {
  return request({
    url: '/agent-process/getListByUniqueId/' + uniqueId,
    method: 'get'
  });
}

/**
 * 设置智能体主流程
 *
 * @param flowId
 */
export function setAgentMainProcess(flowId: string) {
  return request({
    url: '/agent-process/setMainProcess/' + flowId,
    method: 'post'
  });
}

/**
 * 删除智能体流程
 *
 * @param data
 */
export function deleteAgentFlow(flowId: string) {
  return request({
    url: '/agent-process/update/' + flowId + '?type=delete',
    method: 'put'
  });
}

/**
 * 启用智能体流程
 *
 * @param data
 */
export function enableAgentFlow(flowId: string) {
  return request({
    url: '/agent-process/update/' + flowId + '?type=using',
    method: 'put'
  });
}

/**
 * 执行智能体流程
 *
 * @param data
 */
export function executeAgentFlow(obj: any) {
  return request({
    url: '/agent-process-instance/executeAgentProcess',
    method: 'post',
    data: obj
  });
}

/**
 * 查询智能体流程实例列表
 *
 * @param data
 */
export function queryAgentProcessInstanceList(pageDto: any) {
  return request({
    url: '/agent-process-instance/queryList',
    method: 'post',
    data: pageDto
  });
}

/**
 * 查询智能体流程实例详情
 *
 * @param processInstanceId
 */
export function queryAgentProcessInstanceDetail(processInstanceId: string) {
  return request({
    url: '/agent-process-instance/queryDetailByProcessInstanceId?processInstanceId=' + processInstanceId,
    method: 'get'
  });
}
