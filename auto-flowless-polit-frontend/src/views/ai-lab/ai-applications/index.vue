<template>
  <div class="ai-applications-container">
    <el-tabs v-model:activeName="activeTab" type="border-card">
      <el-tab-pane label="RAG (检索增强生成)" name="rag">
        <rag-component ref="ragRef" />
      </el-tab-pane>
      <el-tab-pane label="KAG (知识图谱增强)" name="kag">
        <kag-component ref="kagRef" />
      </el-tab-pane>
      <el-tab-pane label="AGENT (智能代理)" name="agent">
        <agent-component ref="agentRef" />
      </el-tab-pane>
      <el-tab-pane label="EMBEDDING (嵌入服务)" name="embedding">
        <embedding-component ref="embeddingRef" />
      </el-tab-pane>
      <el-tab-pane label="REACT (反应式架构)" name="react">
        <react-component ref="reactRef" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import RagComponent from './rag.vue'
import KagComponent from './kag.vue'
import AgentComponent from './agent.vue'
import EmbeddingComponent from './embedding.vue'
import ReactComponent from './react.vue'

const activeTab = ref('rag')
const ragRef = ref()
const kagRef = ref()
const agentRef = ref()
const embeddingRef = ref()
const reactRef = ref()
</script>

<style scoped>
.ai-applications-container {
  padding: 20px;
}
</style>