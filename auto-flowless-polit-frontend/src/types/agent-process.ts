/**
 * 智能体流程相关类型定义
 */

/**
 * 智能体流程实体
 */
export interface AgentProcess {
  id?: number
  flowId?: string
  name: string
  logo: string
  groupId: number
  formItems: string
  formItemsPc?: string
  process: string
  remark?: string
  sort?: number
  hidden?: boolean
  stop?: boolean
  adminId?: string
  admin: string
  rangeShow?: string
  uniqueId?: string
  version?: number
  agentConfig?: string
  maxSteps?: number
  temperature?: number
  enableThoughtProcess?: boolean
  enableSafetyCheck?: boolean
  createTime?: string
  updateTime?: string
}

/**
 * 智能体流程VO
 */
export interface AgentProcessVO extends AgentProcess {
  selectUserNodeId?: string[]
  rangeList?: NodeUser[]
  variableMap?: Record<string, any>
  publish?: boolean
}

/**
 * 节点用户
 */
export interface NodeUser {
  id?: string
  name?: string
  type?: string
}

/**
 * 智能体流程查询参数
 */
export interface AgentProcessQuery {
  name?: string
  status?: number
  page?: number
  size?: number
}

/**
 * 智能体流程列表响应
 */
export interface AgentProcessListResponse {
  data: AgentProcessVO[]
  total: number
  page: number
  size: number
}

/**
 * 智能体配置
 */
export interface AgentConfig {
  model?: string
  tools?: string[]
  systemPrompt?: string
  maxTokens?: number
  temperature?: number
}

/**
 * 智能体执行结果
 */
export interface AgentExecutionResult {
  id?: string
  processId?: number
  status?: 'pending' | 'running' | 'completed' | 'failed'
  result?: string
  executionTime?: number
  steps?: AgentExecutionStep[]
  createTime?: string
  updateTime?: string
}

/**
 * 智能体执行步骤
 */
export interface AgentExecutionStep {
  id?: string
  stepNumber?: number
  type?: 'thought' | 'action' | 'observation'
  content?: string
  action?: string
  actionInput?: any
  observation?: string
  createTime?: string
}