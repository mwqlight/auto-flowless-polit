<template>
  <div>
    <div class="titlebar">
      <div class="f1">
        <el-text tag="b" size="large" line-clamp="1" type="primary">{{ flowName }}</el-text>
      </div>
      <div class="f2">


			<span class="center_t" effect="dark" :activeStep="activeStep==0" @click="activeStep=0">
						<span :activeStep="activeStep==0">1</span>
						<span>基础信息</span>
				</span>
        <span class="center_t" effect="dark" :activeStep="activeStep==1" @click="activeStep=1">
						<span :activeStep="activeStep==1">2</span>
						<span>表单设计</span>
				</span>
        <span class="center_t" effect="dark" :activeStep="activeStep==2" @click="activeStep=2">
						<span :activeStep="activeStep==2">3</span>
						<span>智能体流程设计</span>
				</span>

      </div>
      <div class="f3">
                <el-button :icon="DocumentAdd" type="warning" @click="publish(false)">暂 存</el-button>
        <el-button :icon="Promotion" type="primary" @click="publish(true)">发 布</el-button>
      </div>
    </div>

    <div style="height: 5px;background-color: white;margin-bottom: 0px"></div>
    <step1 v-show="activeStep === 0" :groupId="paramGroupId" ref="step1Ref"/>
    <step2 v-show="activeStep === 1" ref="step2Ref"/>
    <step3 v-show="activeStep === 2" :nodeConfigObj="step3NodeConfig" ref="step3Ref"/>



    <!--			//验证每一步-->
    <el-dialog v-model="validateDialogShow" :show-close="true" title="智能体流程检查"  >

      <el-steps :active="validateFlowStep" finish-status="success" simple style="margin-top: 0px">
        <el-step title="基础信息"/>
        <el-step title="表单设计"/>
        <el-step title="智能体流程设计"/>

      </el-steps>

      <div style="text-align: center">
        <el-result v-if="validateFlowStep==3&&validateErrMsg.length==0"
                   icon="success"
                   title="检查成功"
                   sub-title="智能体流程检查完成，现在提交？"
        >
          <template #extra>
            <el-button :icon="Select"  :loading="submitLoading" type="primary" @click="submitFlow">提交</el-button>
          </template>
        </el-result>


        <el-result title="检查中" sub-title="正在检查智能体流程信息"
                   v-if="validateErrMsg.length==0&&validateDialogShow&&validatingShow&&validateFlowStep<3">
          <template #icon>
						<span v-loading="true"
                  style="display: inline-block;border:0px solid red;width: 100px;height: 100px;">

					</span>
          </template>

        </el-result>

        <el-result v-if="validateErrMsg.length>0"
                   icon="error"
                   title="检查失败"
        >
          <template #sub-title>
            <div v-for="item in validateErrMsg">
              <el-text type="danger">

                <el-icon>
                  <WarnTriangleFilled/>
                </el-icon>
                {{ item }}
              </el-text>
            </div>
          </template>
          <template #extra>
            <el-button type="primary" @click="gotoEdit">去修改</el-button>
          </template>
        </el-result>

      </div>
    </el-dialog>

  </div>
</template>

<script lang="ts" setup>
import {ref,computed, onMounted, provide} from "vue";
import {DocumentAdd, Promotion,WarnTriangleFilled,Select} from "@element-plus/icons-vue";
import {
  addFlow, getFlowDetail
} from "../../api/flow/index";
import { ElMessage } from "element-plus";


import * as util from "../../utils/objutil";


import Step1 from "../../components/flow/step1.vue";
import Step2 from "../../components/flow/step2.vue";
import Step3 from "../../components/flow/step3.vue";

import {LocationQuery, LocationQueryValue, onBeforeRouteLeave, useRoute, useRouter} from "vue-router";


import {useUserStore} from "../../stores/user";


//表示流程图可编辑
provide("readOnlyAtFlow",false);
// 监听路由离开
onBeforeRouteLeave((to, from, next) => {


  store.$reset();
  next()

})


const userStore = useUserStore();

import {useFlowStore} from "../../stores/flow";

let store = useFlowStore();
const step1Ref = ref();
const step2Ref = ref();
const step3Ref = ref();


const validateErrMsg = ref([])

const flowName = computed(() => {
  let name1 = store.step1.name;
  if (util.isBlank(name1)) {
    return '未命名智能体流程'
  }
  return name1
})

const activeStep = ref(0);
const validateFlowStep = ref(0);
const validateDialogShow = ref(false);
const validatingShow = ref(false);

const gotoEdit = () => {
  activeStep.value = validateFlowStep.value;
  validateDialogShow.value = false
}
//直接发布还是保存
const directPublish=ref(true);


const publish = (p) => {

  directPublish.value=p;

  validateErrMsg.value = []

  validateFlowStep.value = 0
  validateDialogShow.value = true;
  validatingShow.value = true;


  setTimeout(function () {
    //1
    checkStep1();


  }, 500)


}

import {formGroupConfig} from "../../api/form/data";
import {assiginObj, getRandomId} from "../../utils/objutil";


const route = useRoute();
onMounted(() => {
  const query: LocationQuery = route.query;

  const groupId = (query.groupId as LocationQueryValue) ?? "";
  const flowId = (query.flowId as LocationQueryValue) ?? "";
  const id = (query.id as LocationQueryValue) ?? "";
  const cp = (query.cp as LocationQueryValue) ?? "";

	//保存到store
	provide("currentFlowId", flowId);


	if(util.isNotBlank(id)){
  // 编辑模式，加载流程详情
  getFlowDetail(id).then((res) => {
    if (res.code === 200) {
      const flowData = res.data;
      // 将流程数据填充到store中
      store.step1 = {
        name: flowData.name,
        description: flowData.description,
        flowId: flowData.uniqueId,
        groupId: flowData.groupId,
        rangeList: flowData.rangeList || []
      };
      
      store.step2 = {
        formItems: flowData.formItems ? JSON.parse(flowData.formItems) : []
      };
      
      store.step3 = {
        flowConfig: flowData.flowConfig ? JSON.parse(flowData.flowConfig) : {}
      };
    } else {
      ElMessage.error(res.message || '获取流程详情失败');
    }
  }).catch((error) => {
    ElMessage.error('获取流程详情失败：' + error.message);
  });
}

});

// 检查第一步：基础信息
const checkStep1 = () => {
  if (step1Ref.value && step1Ref.value.validateStep1) {
    const result = step1Ref.value.validateStep1();
    if (result.success) {
      validateFlowStep.value = 1;
      setTimeout(() => {
        checkStep2();
      }, 500);
    } else {
      validateErrMsg.value = result.errors;
      validatingShow.value = false;
    }
  }
}

// 检查第二步：表单设计
const checkStep2 = () => {
  if (step2Ref.value && step2Ref.value.validateStep2) {
    const result = step2Ref.value.validateStep2();
    if (result.success) {
      validateFlowStep.value = 2;
      setTimeout(() => {
        checkStep3();
      }, 500);
    } else {
      validateErrMsg.value = result.errors;
      validatingShow.value = false;
    }
  }
}

// 检查第三步：智能体流程设计
const checkStep3 = () => {
  if (step3Ref.value && step3Ref.value.validateStep3) {
    const result = step3Ref.value.validateStep3();
    if (result.success) {
      validateFlowStep.value = 3;
      validatingShow.value = false;
    } else {
      validateErrMsg.value = result.errors;
      validatingShow.value = false;
    }
  }
}

// 提交智能体流程
const submitFlow = () => {
  // 收集所有步骤的数据
  const flowData = {
    name: store.step1.name,
    description: store.step1.description,
    uniqueId: store.step1.flowId,
    groupId: store.step1.groupId,
    formItems: JSON.stringify(store.step2.formItems),
    flowConfig: JSON.stringify(store.step3.flowConfig),
    // 发起人范围数据处理
    rangeList: store.step1.rangeList || []
  };

  // 调用API保存智能体流程
  addFlow(flowData).then((res) => {
    if (res.code === 200) {
      // 保存成功
      ElMessage.success('智能体流程保存成功');
      validateDialogShow.value = false;
      // 跳转到智能体流程列表页面
      router.push('/flyflow/flow/agent-list');
    } else {
      // 保存失败
      ElMessage.error(res.message || '智能体流程保存失败');
    }
  }).catch((error) => {
    ElMessage.error('智能体流程保存失败：' + error.message);
  });
}

const router = useRouter();
</script>

<style scoped>
.titlebar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #f5f7fa;
  border-bottom: 1px solid #e4e7ed;
}

.f1 {
  flex: 1;
}

.f2 {
  display: flex;
  align-items: center;
}

.f3 {
  flex: 1;
  display: flex;
  justify-content: flex-end;
}

.center_t {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 20px;
  cursor: pointer;
  color: #606266;
}

.center_t[activeStep="true"] {
  color: #409eff;
}

.center_t span:first-child {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #e4e7ed;
  color: #fff;
  margin-bottom: 5px;
}

.center_t[activeStep="true"] span:first-child {
  background-color: #409eff;
}
</style>