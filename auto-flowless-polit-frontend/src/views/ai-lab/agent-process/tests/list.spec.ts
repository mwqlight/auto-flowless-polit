import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import AgentProcessList from '../list.vue'
import { listAgentProcess, publishAgentProcess, stopAgentProcess, deleteAgentProcess } from '@/api/ai-lab/agent-process'
import { ElMessage, ElMessageBox } from 'element-plus'

// Mock API calls
vi.mock('@/api/ai-lab/agent-process', () => ({
  listAgentProcess: vi.fn(),
  publishAgentProcess: vi.fn(),
  stopAgentProcess: vi.fn(),
  deleteAgentProcess: vi.fn()
}))

// Mock Element Plus components
vi.mock('element-plus', async () => {
  const actual = await vi.importActual('element-plus')
  return {
    ...actual,
    ElMessage: {
      success: vi.fn(),
      error: vi.fn(),
      info: vi.fn()
    },
    ElMessageBox: {
      confirm: vi.fn()
    }
  }
})

describe('AgentProcessList', () => {
  const mockProcessList = [
    {
      id: 1,
      name: '测试流程1',
      logo: 'logo1.png',
      admin: '管理员1',
      version: 1,
      status: 0,
      createTime: '2023-01-01 10:00:00',
      updateTime: '2023-01-01 10:00:00'
    },
    {
      id: 2,
      name: '测试流程2',
      logo: 'logo2.png',
      admin: '管理员2',
      version: 1,
      status: 1,
      createTime: '2023-01-02 10:00:00',
      updateTime: '2023-01-02 10:00:00'
    }
  ]

  beforeEach(() => {
    // Reset mocks
    vi.clearAllMocks()
    
    // Mock API response
    (listAgentProcess as vi.Mock).mockResolvedValue({
      data: mockProcessList,
      total: 2
    })
  })

  it('renders the component correctly', () => {
    const wrapper = mount(AgentProcessList)
    expect(wrapper.find('.agent-process-list').exists()).toBe(true)
    expect(wrapper.find('.card-header span').text()).toBe('智能体流程列表')
  })

  it('loads process list on mount', async () => {
    mount(AgentProcessList)
    await new Promise(resolve => setTimeout(resolve, 0))
    expect(listAgentProcess).toHaveBeenCalled()
  })

  it('displays process list correctly', async () => {
    const wrapper = mount(AgentProcessList)
    await new Promise(resolve => setTimeout(resolve, 0))
    
    const rows = wrapper.findAll('el-table-row')
    expect(rows.length).toBe(2)
    
    const firstRow = rows[0]
    expect(firstRow.find('el-table-column:nth-child(2)').text()).toContain('测试流程1')
    expect(firstRow.find('el-table-column:nth-child(4)').text()).toContain('管理员1')
  })

  it('handles search correctly', async () => {
    const wrapper = mount(AgentProcessList)
    await new Promise(resolve => setTimeout(resolve, 0))
    
    // Fill search form
    const searchInput = wrapper.find('el-input[placeholder="请输入流程名称"] input')
    await searchInput.setValue('测试流程1')
    
    // Click search button
    const searchButton = wrapper.find('el-button[type="primary"]')
    await searchButton.trigger('click')
    
    await new Promise(resolve => setTimeout(resolve, 0))
    expect(listAgentProcess).toHaveBeenCalledWith({
      name: '测试流程1',
      status: null,
      page: 1,
      size: 10
    })
  })

  it('handles reset correctly', async () => {
    const wrapper = mount(AgentProcessList)
    await new Promise(resolve => setTimeout(resolve, 0))
    
    // Fill search form
    const searchInput = wrapper.find('el-input[placeholder="请输入流程名称"] input')
    await searchInput.setValue('测试流程1')
    
    // Click reset button
    const resetButton = wrapper.findAll('el-button')[1]
    await resetButton.trigger('click')
    
    await new Promise(resolve => setTimeout(resolve, 0))
    expect(listAgentProcess).toHaveBeenCalledWith({
      name: '',
      status: null,
      page: 1,
      size: 10
    })
  })

  it('handles publish process correctly', async () => {
    const wrapper = mount(AgentProcessList)
    await new Promise(resolve => setTimeout(resolve, 0))
    
    // Mock publish API response
    (publishAgentProcess as vi.Mock).mockResolvedValue({})
    
    // Click publish button on second row (status 1)
    const publishButton = wrapper.findAll('el-button[type="success"]')[0]
    await publishButton.trigger('click')
    
    await new Promise(resolve => setTimeout(resolve, 0))
    expect(publishAgentProcess).toHaveBeenCalledWith(2)
    expect(ElMessage.success).toHaveBeenCalledWith('流程发布成功')
  })

  it('handles stop process correctly', async () => {
    const wrapper = mount(AgentProcessList)
    await new Promise(resolve => setTimeout(resolve, 0))
    
    // Mock stop API response
    (stopAgentProcess as vi.Mock).mockResolvedValue({})
    
    // Click stop button on first row (status 0)
    const stopButton = wrapper.findAll('el-button[type="warning"]')[0]
    await stopButton.trigger('click')
    
    await new Promise(resolve => setTimeout(resolve, 0))
    expect(stopAgentProcess).toHaveBeenCalledWith(1)
    expect(ElMessage.success).toHaveBeenCalledWith('流程停用成功')
  })

  it('handles delete process correctly', async () => {
    const wrapper = mount(AgentProcessList)
    await new Promise(resolve => setTimeout(resolve, 0))
    
    // Mock delete API response
    (deleteAgentProcess as vi.Mock).mockResolvedValue({})
    
    // Mock MessageBox confirm
    (ElMessageBox.confirm as vi.Mock).mockResolvedValue('confirm')
    
    // Click delete button on first row
    const deleteButton = wrapper.findAll('el-button[type="danger"]')[0]
    await deleteButton.trigger('click')
    
    await new Promise(resolve => setTimeout(resolve, 0))
    expect(ElMessageBox.confirm).toHaveBeenCalled()
    expect(deleteAgentProcess).toHaveBeenCalledWith(1)
    expect(ElMessage.success).toHaveBeenCalledWith('流程删除成功')
  })
})