import { RouteRecordRaw } from 'vue-router'
import AgentProcessList from '@/views/ai-lab/agent-process/list.vue'
import AgentProcessCreate from '@/views/ai-lab/agent-process/create.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/ai-lab/agent-process',
    name: 'AgentProcess',
    component: AgentProcessList,
    meta: {
      title: '智能体流程',
      icon: 'icon-process',
      hidden: false,
      breadcrumb: true,
      affix: false,
      keepAlive: true,
      alwaysShow: true,
      roles: ['admin', 'user']
    },
    children: [
      {
        path: 'create',
        name: 'AgentProcessCreate',
        component: AgentProcessCreate,
        meta: {
          title: '创建智能体流程',
          icon: 'icon-create',
          hidden: true,
          breadcrumb: true,
          affix: false,
          keepAlive: true,
          alwaysShow: false,
          roles: ['admin', 'user']
        }
      },
      {
        path: 'edit/:id',
        name: 'AgentProcessEdit',
        component: AgentProcessCreate,
        meta: {
          title: '编辑智能体流程',
          icon: 'icon-edit',
          hidden: true,
          breadcrumb: true,
          affix: false,
          keepAlive: true,
          alwaysShow: false,
          roles: ['admin', 'user']
        }
      }
    ]
  }
]

export default routes