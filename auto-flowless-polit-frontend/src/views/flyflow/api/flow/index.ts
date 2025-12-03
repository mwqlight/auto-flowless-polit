import request from '../../utils/request';




/**
 * 创建智能体流程
 *
 * @param data
 */
export function addFlow(data: any) {
	return request({
		url: '/agentProcess/create',
		method: 'post',
		data: data
	});
}

/**
 * 获取智能体流程详细信息
 *
 * @param data
 */
export function getFlowDetail(flowId: string) {
	return request({
		url: '/agentProcess/getDetail?flowId=' + flowId,
		method: 'get'
	});
}

/**
 * 停用智能体流程
 *
 * @param data
 */
export function disableFlow(flowId: string) {
	return request({
		url: '/agentProcess/update/' + flowId + "?type=stop",
		method: 'put'
	});
}

/**
 * 根据智能体流程唯一标识查询流程列表
 *
 * @param uniqueId
 */
export function getListByUniqueId(uniqueId: string) {
	return request({
		url: '/agentProcess/getListByUniqueId/' + uniqueId ,
		method: 'get'
	});
}

/**
 * 设置主智能体流程
 *
 * @param flowId
 */
export function setMainProcess(flowId: string) {
	return request({
		url: '/agentProcess/setMainProcess/' + flowId ,
		method: 'post'
	});
}

/**
 * 删除智能体流程
 *
 * @param data
 */
export function deleteFlow(flowId: string) {
	return request({
		url: '/agentProcess/update/' + flowId + "?type=delete",
		method: 'put'
	});
}

/**
 * 启用智能体流程
 *
 * @param data
 */
export function enableFlow(flowId: string) {
	return request({
		url: '/agentProcess/update/' + flowId + "?type=using",
		method: 'put'
	});
}

/**
 * 发起流程
 *
 * @param data
 */
export function startFlow(obj: any) {
	return request({
		url: '/process-instance/startProcessInstance',
		method: 'post',
		data: obj
	});
}

/**
 * 获取智能体流程组列表
 *
 * @param data
 */
export function getAgentProcessGroupList() {
	return request({
		url: '/agentProcessGroup/list',
		method: 'get'
	});
}

/**
 * 创建智能体流程组
 *
 * @param data
 */
export function createAgentProcessGroup(data: any) {
	return request({
		url: '/agentProcessGroup/create',
		method: 'post',
		data: data
	});
}

/**
 * 编辑智能体流程组
 *
 * @param data
 */
export function editAgentProcessGroup(data: any) {
	return request({
		url: '/agentProcessGroup/edit',
		method: 'post',
		data: data
	});
}

/**
 * 删除智能体流程组
 *
 * @param id
 */
export function deleteAgentProcessGroup(id: number) {
	return request({
		url: '/agentProcessGroup/delete/' + id,
		method: 'delete'
	});
}

/**
 * 智能体流程组上移排序
 *
 * @param data
 */
export function agentProcessGroupTopSort(data: any) {
	return request({
		url: '/agentProcessGroup/topSort',
		method: 'post',
		data: data
	});
}

/**
 * 智能体流程组下移排序
 *
 * @param data
 */
export function agentProcessGroupBottomSort(data: any) {
	return request({
		url: '/agentProcessGroup/bottomSort',
		method: 'post',
		data: data
	});
}
