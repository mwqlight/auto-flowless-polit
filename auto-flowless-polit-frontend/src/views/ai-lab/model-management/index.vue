<template>
  <div class="app-container">
    <el-card>
      <div class="card-header">
        <h2>AI模型管理</h2>
        <el-button type="primary" @click="handleAddModel">添加模型</el-button>
      </div>
      <el-table :data="modelList" stripe border>
        <el-table-column prop="id" label="模型ID" width="100" />
        <el-table-column prop="name" label="模型名称" />
        <el-table-column prop="type" label="模型类型" />
        <el-table-column prop="version" label="版本" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 'active' ? 'success' : 'info'">
              {{ scope.row.status === 'active' ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEditModel(scope.row)">
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="handleDeleteModel(scope.row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑模型对话框 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="500px"
      @close="resetForm"
    >
      <el-form :model="modelForm" :rules="modelRules" ref="modelFormRef" label-width="100px">
        <el-form-item label="模型名称" prop="name">
          <el-input v-model="modelForm.name" placeholder="请输入模型名称" />
        </el-form-item>
        <el-form-item label="模型类型" prop="type">
          <el-select v-model="modelForm.type" placeholder="请选择模型类型">
            <el-option label="LLM" value="llm" />
            <el-option label="Embedding" value="embedding" />
            <el-option label="RAG" value="rag" />
            <el-option label="Agent" value="agent" />
          </el-select>
        </el-form-item>
        <el-form-item label="版本" prop="version">
          <el-input v-model="modelForm.version" placeholder="请输入模型版本" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="modelForm.status">
            <el-radio label="active">启用</el-radio>
            <el-radio label="inactive">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="模型描述">
          <el-input v-model="modelForm.description" type="textarea" placeholder="请输入模型描述" rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">提交</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

interface Model {
  id: number
  name: string
  type: string
  version: string
  status: 'active' | 'inactive'
  description?: string
  createTime: string
}

const modelList = ref<Model[]>([
  {
    id: 1,
    name: 'GPT-4',
    type: 'llm',
    version: '1.0',
    status: 'active',
    description: 'OpenAI GPT-4语言模型',
    createTime: '2024-01-01 10:00:00'
  },
  {
    id: 2,
    name: 'text-embedding-ada-002',
    type: 'embedding',
    version: '1.0',
    status: 'active',
    description: 'OpenAI文本嵌入模型',
    createTime: '2024-01-02 14:30:00'
  }
])

const dialogVisible = ref(false)
const dialogTitle = ref('添加模型')
const modelFormRef = ref()
const modelForm = reactive({
  name: '',
  type: '',
  version: '',
  status: 'active',
  description: ''
})

const modelRules = {
  name: [
    { required: true, message: '请输入模型名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择模型类型', trigger: 'change' }
  ],
  version: [
    { required: true, message: '请输入模型版本', trigger: 'blur' },
    { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
  ]
}

const handleAddModel = () => {
  dialogTitle.value = '添加模型'
  resetForm()
  dialogVisible.value = true
}

const handleEditModel = (model: Model) => {
  dialogTitle.value = '编辑模型'
  Object.assign(modelForm, model)
  dialogVisible.value = true
}

const handleDeleteModel = (id: number) => {
  ElMessageBox.confirm('确定要删除该模型吗？', '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      const index = modelList.value.findIndex(model => model.id === id)
      if (index !== -1) {
        modelList.value.splice(index, 1)
        ElMessage.success('模型删除成功')
      }
    })
    .catch(() => {
      // 取消删除
    })
}

const resetForm = () => {
  modelForm.name = ''
  modelForm.type = ''
  modelForm.version = ''
  modelForm.status = 'active'
  modelForm.description = ''
  modelFormRef.value?.resetFields()
}

const handleSubmit = () => {
  modelFormRef.value?.validate((valid: boolean) => {
    if (valid) {
      if (modelForm.id) {
        // 编辑模型
        const index = modelList.value.findIndex(model => model.id === modelForm.id)
        if (index !== -1) {
          modelList.value[index] = { ...modelForm as Model, createTime: modelList.value[index].createTime }
          ElMessage.success('模型编辑成功')
        }
      } else {
        // 添加模型
        const newModel: Model = {
          ...modelForm as Omit<Model, 'id' | 'createTime'>,
          id: Date.now(),
          createTime: new Date().toISOString().slice(0, 19).replace('T', ' ')
        }
        modelList.value.push(newModel)
        ElMessage.success('模型添加成功')
      }
      dialogVisible.value = false
    }
  })
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>