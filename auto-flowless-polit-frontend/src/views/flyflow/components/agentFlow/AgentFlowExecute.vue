<template>
  <div class="agent-flow-execute">
    <div class="form-section">
      <h3>表单数据</h3>
      <el-form
        ref="executeFormRef"
        :model="executeForm"
        label-width="120px"
        class="form"
      >
        <el-form-item
          v-for="(field, index) in formFields"
          :key="index"
          :label="field.label"
          :prop="field.field"
          :rules="field.rules"
        >
          <el-input
            v-if="field.type === 'text' || field.type === 'number' || field.type === 'password'"
            v-model="executeForm[field.field]"
            :placeholder="field.placeholder || `请输入${field.label}`"
            :type="field.type"
            :disabled="field.disabled"
          />
          <el-select
            v-else-if="field.type === 'select'"
            v-model="executeForm[field.field]"
            :placeholder="field.placeholder || `请选择${field.label}`"
            :disabled="field.disabled"
          >
            <el-option
              v-for="option in field.options || []"
              :key="option.value"
              :label="option.label"
              :value="option.value"
            />
          </el-select>
          <el-date-picker
            v-else-if="field.type === 'date'"
            v-model="executeForm[field.field]"
            :placeholder="field.placeholder || `请选择${field.label}`"
            :disabled="field.disabled"
            style="width: 100%"
          />
          <el-input
            v-else-if="field.type === 'textarea'"
            v-model="executeForm[field.field]"
            :placeholder="field.placeholder || `请输入${field.label}`"
            :rows="field.rows || 3"
            :disabled="field.disabled"
            type="textarea"
          />
          <el-switch
            v-else-if="field.type === 'switch'"
            v-model="executeForm[field.field]"
            :disabled="field.disabled"
            :active-text="field.activeText || '是'"
            :inactive-text="field.inactiveText || '否'"
          />
          <el-input
            v-else
            v-model="executeForm[field.field]"
            :placeholder="field.placeholder || `请输入${field.label}`"
            :disabled="field.disabled"
          />
        </el-form-item>
      </el-form>
    </div>

    <div class="execute-section" v-if="executionResult">
      <h3>执行结果</h3>
      <div class="result-card">
        <div class="result-item">
          <span class="label">执行状态：</span>
          <el-tag :type="executionResult.status === 'success' ? 'success' : 'danger'">
            {{ executionResult.status === 'success' ? '成功' : '失败' }}
          </el-tag>
        </div>
        <div class="result-item" v-if="executionResult.message">
          <span class="label">执行信息：</span>
          <span class="value">{{ executionResult.message }}</span>
        </div>
        <div class="result-item" v-if="executionResult.processInstanceId">
          <span class="label">流程实例ID：</span>
          <span class="value">{{ executionResult.processInstanceId }}</span>
        </div>
        <div class="result-item" v-if="executionResult.executionTime">
          <span class="label">执行时间：</span>
          <span class="value">{{ executionResult.executionTime }}</span>
        </div>
      </div>
    </div>

    <div class="execute-buttons">
      <el-button type="primary" @click="handleExecute" :loading="executing">
        <el-icon><Play /></el-icon>
        执行流程
      </el-button>
      <el-button @click="$emit('cancel')">取消</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { Play } from '@element-plus/icons-vue';
import { executeAgentFlow, getAgentFlowDetail } from '../../api/agentFlow';

const props = defineProps<{
  flowId: string;
}>();

const emit = defineEmits<{
  (e: 'success'): void;
  (e: 'cancel'): void;
}>();

const executeFormRef = ref<any>(null);
const executing = ref(false);
const formFields = ref<any[]>([]);
const executeForm = ref<any>({});
const executionResult = ref<any>(null);

// 解析表单JSON生成表单字段
const parseFormJson = (formJson: string) => {
  try {
    const formConfig = JSON.parse(formJson);
    if (formConfig.fields && Array.isArray(formConfig.fields)) {
      formFields.value = formConfig.fields;
      // 初始化表单数据
      formConfig.fields.forEach((field: any) => {
        executeForm.value[field.field] = field.defaultValue || '';
      });
    }
  } catch (error) {
    ElMessage.error('解析表单配置失败：' + error);
    console.error('解析表单配置失败：', error);
  }
};

// 获取智能体流程详情
const getAgentFlowDetailInfo = async () => {
  try {
    const response = await getAgentFlowDetail(props.flowId);
    if (response.code === 200) {
      const data = response.data;
      if (data.formJson) {
        parseFormJson(data.formJson);
      }
    } else {
      ElMessage.error('获取智能体流程详情失败：' + response.message);
    }
  } catch (error) {
    ElMessage.error('获取智能体流程详情失败');
    console.error('获取智能体流程详情失败：', error);
  }
};

// 执行智能体流程
const handleExecute = async () => {
  const isValid = await executeFormRef.value?.validate();
  if (!isValid) return;

  executing.value = true;
  try {
    const params = {
      flowId: props.flowId,
      formData: executeForm.value
    };

    const response = await executeAgentFlow(params);
    if (response.code === 200) {
      ElMessage.success('智能体流程执行成功');
      // 保存执行结果
      executionResult.value = {
        status: 'success',
        message: response.message || '执行成功',
        processInstanceId: response.data?.processInstanceId,
        executionTime: new Date().toLocaleString()
      };
      emit('success');
    } else {
      ElMessage.error('智能体流程执行失败：' + response.message);
      // 保存执行结果
      executionResult.value = {
        status: 'failed',
        message: response.message || '执行失败',
        executionTime: new Date().toLocaleString()
      };
    }
  } catch (error: any) {
    ElMessage.error('智能体流程执行失败：' + (error.message || '未知错误'));
    console.error('执行智能体流程失败：', error);
    // 保存执行结果
    executionResult.value = {
      status: 'failed',
      message: error.message || '执行失败',
      executionTime: new Date().toLocaleString()
    };
  } finally {
    executing.value = false;
  }
};

onMounted(() => {
  getAgentFlowDetailInfo();
});
</script>

<style scoped>
.agent-flow-execute {
  padding: 20px;
}

.form-section {
  margin-bottom: 30px;
}

.form-section h3 {
  margin-bottom: 15px;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.form {
  background: #f5f7fa;
  padding: 20px;
  border-radius: 8px;
  max-height: 400px;
  overflow-y: auto;
}

.execute-section {
  margin-bottom: 30px;
}

.execute-section h3 {
  margin-bottom: 15px;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.result-card {
  background: #f5f7fa;
  padding: 20px;
  border-radius: 8px;
}

.result-item {
  margin-bottom: 12px;
  display: flex;
  align-items: center;
}

.result-item:last-child {
  margin-bottom: 0;
}

.label {
  font-weight: 500;
  color: #606266;
  min-width: 100px;
}

.value {
  color: #303133;
  flex: 1;
}

.execute-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding-top: 20px;
  border-top: 1px solid #e4e7ed;
}
</style>
