import request from '@/utils/request'

/**
 * 创建智能体流程
 * @param data 智能体流程数据
 */
export const createAgentProcess = (data: any) => {
  return request({
    url: '/api/agent-process/create',
    method: 'post',
    data
  })
}

/**
 * 更新智能体流程
 * @param data 智能体流程数据
 */
export const updateAgentProcess = (data: any) => {
  return request({
    url: '/api/agent-process/update',
    method: 'post',
    data
  })
}

/**
 * 根据ID查询智能体流程
 * @param id 流程ID
 */
export const getAgentProcessById = (id: number) => {
  return request({
    url: `/api/agent-process/getById/${id}`,
    method: 'get'
  })
}

/**
 * 根据唯一标识查询智能体流程
 * @param uniqueId 唯一标识
 */
export const getAgentProcessByUniqueId = (uniqueId: string) => {
  return request({
    url: `/api/agent-process/getByUniqueId/${uniqueId}`,
    method: 'get'
  })
}

/**
 * 查询智能体流程列表
 * @param params 查询参数
 */
export const listAgentProcess = (params: any) => {
  return request({
    url: '/api/agent-process/list',
    method: 'post',
    data: params
  })
}

/**
 * 发布智能体流程
 * @param id 流程ID
 */
export const publishAgentProcess = (id: number) => {
  return request({
    url: `/api/agent-process/publish/${id}`,
    method: 'post'
  })
}

/**
 * 停用智能体流程
 * @param id 流程ID
 */
export const stopAgentProcess = (id: number) => {
  return request({
    url: `/api/agent-process/stop/${id}`,
    method: 'post'
  })
}

/**
 * 删除智能体流程
 * @param id 流程ID
 */
export const deleteAgentProcess = (id: number) => {
  return request({
    url: `/api/agent-process/delete/${id}`,
    method: 'post'
  })
}