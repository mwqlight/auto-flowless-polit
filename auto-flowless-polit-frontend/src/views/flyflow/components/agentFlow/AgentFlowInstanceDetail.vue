<template>
  <div class="agent-flow-instance-detail">
    <el-tabs v-model="activeTab" class="tabs">
      <el-tab-pane label="基本信息" name="baseInfo">
        <div class="base-info">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form label-width="120px" class="form">
                <el-form-item label="实例ID">
                  <span class="value">{{ instanceDetail.processInstanceId }}</span>
                </el-form-item>
                <el-form-item label="流程名称">
                  <span class="value">{{ instanceDetail.flowName }}</span>
                </el-form-item>
                <el-form-item label="流程ID">
                  <span class="value">{{ instanceDetail.flowId }}</span>
                </el-form-item>
                <el-form-item label="状态">
                  <el-tag
                    :type="getStatusTagType(instanceDetail.status)"
                  >
                    {{ getStatusText(instanceDetail.status) }}
                  </el-tag>
                </el-form-item>
                <el-form-item label="发起人">
                  <span class="value">{{ instanceDetail.initiator }}</span>
                </el-form-item>
                <el-form-item label="执行时间">
                  <span class="value">{{ instanceDetail.executionTime }}</span>
                </el-form-item>
              </el-form>
            </el-col>
            <el-col :span="12">
              <el-form label-width="120px" class="form">
                <el-form-item label="完成时间">
                  <span class="value">{{ instanceDetail.completionTime || '未完成' }}</span>
                </el-form-item>
                <el-form-item label="执行时长">
                  <span class="value">{{ instanceDetail.duration || '0秒' }}</span>
                </el-form-item>
                <el-form-item label="执行结果">
                  <el-tag
                    :type="instanceDetail.result === 'success' ? 'success' : 'danger'"
                  >
                    {{ instanceDetail.result === 'success' ? '成功' : '失败' }}
                  </el-tag>
                </el-form-item>
                <el-form-item label="错误信息">
                  <span class="value error-text">{{ instanceDetail.errorMessage || '无' }}</span>
                </el-form-item>
                <el-form-item label="创建时间">
                  <span class="value">{{ instanceDetail.createTime }}</span>
                </el-form-item>
                <el-form-item label="更新时间">
                  <span class="value">{{ instanceDetail.updateTime }}</span>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>

      <el-tab-pane label="表单数据" name="formData">
        <div class="form-data">
          <el-form label-width="120px" class="form">
            <el-form-item
              v-for="(value, key) in instanceDetail.formData"
              :key="key"
              :label="getFieldLabel(key)"
            >
              <span class="value">{{ formatValue(value) }}</span>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>

      <el-tab-pane label="执行日志" name="executionLog">
        <div class="execution-log">
          <el-table
            v-loading="logLoading"
            :data="executionLog"
            stripe
            border
            style="width: 100%"
          >
            <el-table-column prop="nodeName" label="节点名称" min-width="150" />
            <el-table-column prop="nodeType" label="节点类型" min-width="100">
              <template #default="scope">
                <el-tag
                  :type="getNodeTypeTagType(scope.row.nodeType)"
                >
                  {{ getNodeTypeText(scope.row.nodeType) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="执行状态" min-width="100">
              <template #default="scope">
                <el-tag
                  :type="getStatusTagType(scope.row.status)"
                >
                  {{ getStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="startTime" label="开始时间" min-width="160" />
            <el-table-column prop="endTime" label="结束时间" min-width="160" />
            <el-table-column prop="duration" label="执行时长" min-width="120" />
            <el-table-column prop="result" label="执行结果" min-width="120">
              <template #default="scope">
                <el-tag
                  :type="scope.row.result === 'success' ? 'success' : 'danger'"
                >
                  {{ scope.row.result === 'success' ? '成功' : '失败' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="errorMessage" label="错误信息" min-width="200" show-overflow-tooltip />
          </el-table>
        </div>
      </el-tab-pane>

      <el-tab-pane label="流程变量" name="variables">
        <div class="variables">
          <el-table
            v-loading="variablesLoading"
            :data="variablesList"
            stripe
            border
            style="width: 100%"
          >
            <el-table-column prop="variableName" label="变量名称" min-width="150" />
            <el-table-column prop="variableValue" label="变量值" min-width="200" show-overflow-tooltip />
            <el-table-column prop="variableType" label="变量类型" min-width="100" />
            <el-table-column prop="description" label="变量描述" min-width="200" show-overflow-tooltip />
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>

    <div class="detail-buttons">
      <el-button @click="$emit('close')">关闭</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { ElMessage } from 'element-plus';
import { queryAgentProcessInstanceDetail } from '../../api/agentFlow';

const props = defineProps<{
  processInstanceId: string;
}>();

const emit = defineEmits<{
  (e: 'close'): void;
}>();

const activeTab = ref('baseInfo');
const loading = ref(false);
const logLoading = ref(false);
const variablesLoading = ref(false);
const instanceDetail = ref<any>({});
const executionLog = ref<any[]>([]);
const variablesList = ref<any[]>([]);
const formFields = ref<any[]>([]);

// 获取状态标签类型
const getStatusTagType = (status: string) => {
  switch (status) {
    case 'running':
      return 'warning';
    case 'completed':
      return 'success';
    case 'terminated':
      return 'info';
    case 'failed':
      return 'danger';
    default:
      return 'info';
  }
};

// 获取状态文本
const getStatusText = (status: string) => {
  switch (status) {
    case 'running':
      return '运行中';
    case 'completed':
      return '已完成';
    case 'terminated':
      return '已终止';
    case 'failed':
      return '失败';
    default:
      return status;
  }
};

// 获取节点类型标签类型
const getNodeTypeTagType = (nodeType: string) => {
  switch (nodeType) {
    case 'start':
      return 'primary';
    case 'end':
      return 'success';
    case 'service':
      return 'info';
    case 'decision':
      return 'warning';
    case 'userTask':
      return 'danger';
    default:
      return 'info';
  }
};

// 获取节点类型文本
const getNodeTypeText = (nodeType: string) => {
  switch (nodeType) {
    case 'start':
      return '开始节点';
    case 'end':
      return '结束节点';
    case 'service':
      return '服务节点';
    case 'decision':
      return '决策节点';
    case 'userTask':
      return '用户任务节点';
    default:
      return nodeType;
  }
};

// 获取字段标签
const getFieldLabel = (fieldName: string) => {
  const field = formFields.value.find(f => f.field === fieldName);
  return field?.label || fieldName;
};

// 格式化值
const formatValue = (value: any) => {
  if (value === null || value === undefined) {
    return '无';
  }
  if (typeof value === 'object') {
    return JSON.stringify(value, null, 2);
  }
  return String(value);
};

// 获取智能体流程实例详情
const getAgentFlowInstanceDetail = async () => {
  if (!props.processInstanceId) return;

  loading.value = true;
  try {
    const response = await queryAgentProcessInstanceDetail(props.processInstanceId);
    if (response.code === 200) {
      instanceDetail.value = response.data;
      // 解析表单字段
      if (instanceDetail.value.formJson) {
        try {
          const formConfig = JSON.parse(instanceDetail.value.formJson);
          formFields.value = formConfig.fields || [];
        } catch (error) {
          console.error('解析表单配置失败：', error);
        }
      }
      // 获取执行日志
      await getExecutionLog();
      // 获取流程变量
      await getVariables();
    } else {
      ElMessage.error('获取智能体流程实例详情失败：' + response.message);
    }
  } catch (error) {
    ElMessage.error('获取智能体流程实例详情失败');
    console.error('获取智能体流程实例详情失败：', error);
  } finally {
    loading.value = false;
  }
};

// 获取执行日志
const getExecutionLog = async () => {
  logLoading.value = true;
  try {
    // TODO: 调用获取执行日志的API
    // 模拟数据
    executionLog.value = [
      {
        nodeName: '开始节点',
        nodeType: 'start',
        status: 'completed',
        startTime: '2023-10-01 10:00:00',
        endTime: '2023-10-01 10:00:01',
        duration: '1秒',
        result: 'success',
        errorMessage: ''
      },
      {
        nodeName: '服务节点1',
        nodeType: 'service',
        status: 'completed',
        startTime: '2023-10-01 10:00:01',
        endTime: '2023-10-01 10:00:05',
        duration: '4秒',
        result: 'success',
        errorMessage: ''
      },
      {
        nodeName: '决策节点',
        nodeType: 'decision',
        status: 'completed',
        startTime: '2023-10-01 10:00:05',
        endTime: '2023-10-01 10:00:06',
        duration: '1秒',
        result: 'success',
        errorMessage: ''
      },
      {
        nodeName: '服务节点2',
        nodeType: 'service',
        status: 'completed',
        startTime: '2023-10-01 10:00:06',
        endTime: '2023-10-01 10:00:10',
        duration: '4秒',
        result: 'success',
        errorMessage: ''
      },
      {
        nodeName: '结束节点',
        nodeType: 'end',
        status: 'completed',
        startTime: '2023-10-01 10:00:10',
        endTime: '2023-10-01 10:00:11',
        duration: '1秒',
        result: 'success',
        errorMessage: ''
      }
    ];
  } catch (error) {
    ElMessage.error('获取执行日志失败');
    console.error('获取执行日志失败：', error);
  } finally {
    logLoading.value = false;
  }
};

// 获取流程变量
const getVariables = async () => {
  variablesLoading.value = true;
  try {
    // TODO: 调用获取流程变量的API
    // 模拟数据
    variablesList.value = [
      {
        variableName: 'userId',
        variableValue: '123456',
        variableType: 'String',
        description: '用户ID'
      },
      {
        variableName: 'userName',
        variableValue: '张三',
        variableType: 'String',
        description: '用户名'
      },
      {
        variableName: 'age',
        variableValue: '25',
        variableType: 'Integer',
        description: '年龄'
      },
      {
        variableName: 'amount',
        variableValue: '1000.00',
        variableType: 'Double',
        description: '金额'
      }
    ];
  } catch (error) {
    ElMessage.error('获取流程变量失败');
    console.error('获取流程变量失败：', error);
  } finally {
    variablesLoading.value = false;
  }
};

onMounted(() => {
  getAgentFlowInstanceDetail();
});
</script>

<style scoped>
.agent-flow-instance-detail {
  padding: 20px;
}

.tabs {
  margin-bottom: 20px;
}

.base-info {
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
}

.form-data {
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
}

.execution-log {
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
}

.variables {
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
}

.form {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
}

.value {
  color: #303133;
}

.error-text {
  color: #f56c6c;
}

.detail-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding-top: 20px;
  border-top: 1px solid #e4e7ed;
}
</style>
