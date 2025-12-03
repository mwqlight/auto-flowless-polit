# 智能体流程模块

## 概述

智能体流程模块是一个基于 Vue3 + TypeScript 的前端组件，用于创建和管理智能体流程。该模块提供了流程创建、编辑、查看、发布、停用和删除等功能。

## 功能特性

- **流程创建**：通过四步式向导创建智能体流程
- **流程编辑**：编辑已有的智能体流程
- **流程查看**：查看智能体流程的详细信息
- **流程发布**：发布智能体流程，使其可以被执行
- **流程停用**：停用智能体流程，使其无法被执行
- **流程删除**：删除智能体流程
- **流程列表**：显示所有智能体流程的列表
- **搜索功能**：根据流程名称和状态搜索智能体流程
- **分页功能**：分页显示智能体流程列表

## 技术栈

- **Vue3**：前端框架
- **TypeScript**：类型系统
- **Element Plus**：UI组件库
- **Vite**：构建工具
- **Vitest**：测试框架
- **Vue Test Utils**：Vue组件测试工具

## 目录结构

```
agent-process/
├── create.vue          # 流程创建/编辑页面
├── list.vue            # 流程列表页面
├── tests/              # 测试用例
│   └── list.spec.ts    # 流程列表页面测试用例
└── README.md           # 模块说明文档
```

## 页面组件

### 流程创建/编辑页面 (create.vue)

四步式向导：
1. **基础信息**：设置流程名称、图标、分组、备注和管理员
2. **智能体配置**：设置最大执行步骤、温度参数、启用思考过程、启用安全检查和智能体配置
3. **流程设计**：设置表单设置内容、PC表单内容和流程设置内容
4. **完成**：显示创建成功信息

### 流程列表页面 (list.vue)

- **搜索表单**：根据流程名称和状态搜索智能体流程
- **流程列表**：显示智能体流程的列表，包括ID、名称、图标、管理员、版本、状态、创建时间和更新时间
- **操作按钮**：查看、编辑、发布、停用和删除
- **分页组件**：分页显示智能体流程列表

## API接口

### 创建智能体流程

```typescript
import { createAgentProcess } from '@/api/ai-lab/agent-process'

const data = {
  name: '测试流程',
  logo: 'logo.png',
  groupId: 1,
  admin: '管理员',
  maxSteps: 10,
  temperature: 0.7,
  enableThoughtProcess: true,
  enableSafetyCheck: true,
  formItems: '表单设置内容',
  process: '流程设置内容'
}

const result = await createAgentProcess(data)
```

### 更新智能体流程

```typescript
import { updateAgentProcess } from '@/api/ai-lab/agent-process'

const data = {
  id: 1,
  name: '测试流程',
  logo: 'logo.png',
  groupId: 1,
  admin: '管理员',
  maxSteps: 10,
  temperature: 0.7,
  enableThoughtProcess: true,
  enableSafetyCheck: true,
  formItems: '表单设置内容',
  process: '流程设置内容'
}

const result = await updateAgentProcess(data)
```

### 根据ID查询智能体流程

```typescript
import { getAgentProcessById } from '@/api/ai-lab/agent-process'

const result = await getAgentProcessById(1)
```

### 根据唯一标识查询智能体流程

```typescript
import { getAgentProcessByUniqueId } from '@/api/ai-lab/agent-process'

const result = await getAgentProcessByUniqueId('unique-id')
```

### 查询智能体流程列表

```typescript
import { listAgentProcess } from '@/api/ai-lab/agent-process'

const params = {
  name: '测试流程',
  status: 0,
  page: 1,
  size: 10
}

const result = await listAgentProcess(params)
```

### 发布智能体流程

```typescript
import { publishAgentProcess } from '@/api/ai-lab/agent-process'

const result = await publishAgentProcess(1)
```

### 停用智能体流程

```typescript
import { stopAgentProcess } from '@/api/ai-lab/agent-process'

const result = await stopAgentProcess(1)
```

### 删除智能体流程

```typescript
import { deleteAgentProcess } from '@/api/ai-lab/agent-process'

const result = await deleteAgentProcess(1)
```

## 类型定义

### AgentProcess

```typescript
interface AgentProcess {
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
```

### AgentProcessVO

```typescript
interface AgentProcessVO extends AgentProcess {
  selectUserNodeId?: string[]
  rangeList?: NodeUser[]
  variableMap?: Record<string, any>
  publish?: boolean
}
```

### AgentProcessQuery

```typescript
interface AgentProcessQuery {
  name?: string
  status?: number
  page?: number
  size?: number
}
```

## 测试

### 运行测试

```bash
npm run test:unit -- agent-process
```

### 测试用例

- 组件渲染测试
- 流程列表加载测试
- 搜索功能测试
- 重置功能测试
- 发布流程测试
- 停用流程测试
- 删除流程测试

## 使用说明

### 安装依赖

```bash
npm install
```

### 启动开发服务器

```bash
npm run dev
```

### 构建生产版本

```bash
npm run build
```

### 运行测试

```bash
npm run test:unit
```

## 注意事项

- 智能体流程的创建需要填写所有必填项
- 智能体流程的发布和停用需要有相应的权限
- 智能体流程的删除需要二次确认
- 智能体流程的列表支持分页和搜索

## 版本历史

### v1.0.0

- 初始版本
- 支持流程创建、编辑、查看、发布、停用和删除
- 支持流程列表和搜索功能
- 支持分页功能
- 支持测试用例

## 贡献

欢迎提交 Issue 和 Pull Request 来改进这个模块。

## 许可证

MIT License