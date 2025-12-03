<template>
	<div class="login-container">
		<div class="login-content">
			<div class="logo">AFP</div>
			<div class="title">Auto Flowless Polit</div>
			<div class="loading"></div>
		</div>
	</div>
</template>

<script setup lang="ts">

import {getLoginUrl} from "@/api/auth";
import {LocationQuery, LocationQueryValue, useRoute} from "vue-router";
import router from "@/router";

import {useUserStore} from "@/views/flyflow/stores/user";
import {usePermissionStoreHook} from "@/store/modules/permission";
import {isBlank, isNotBlank, parseUrlParams,assiginObj} from "@/views/flyflow/utils/objutil";
import {isMobile, isWxCp} from "@/views/flyflow/utils/appversion";

const permissionStore = usePermissionStoreHook();

const userStore = useUserStore();
const route = useRoute();

function handleLogin() {
	getLoginUrl().then(res => {

		let params = getParams();




		let data = res.data;
		let captcha = data.captcha;
		if (data.innerUrl) {
			router.push({path: data.url, query:assiginObj( params.params,{redirect:params.redirect,captcha:captcha})});

		} else {
			window.location.href = data.url
		}


	})
}


//获取所有的参数
function getParams() {
	const query = route.query;
	let params = route.params;

	var redirect = (query.redirect) ?? "/dashboard";

	const otherQueryParams = Object.keys(query).reduce(
			(acc: any, cur: string) => {
				if (cur !== "redirect") {
					acc[cur] = query[cur];
				}
				return acc;
			},
			{}
	);
	if (params) {
		let r = params.r;//跳转地址
		let pname = params.pname;//参数key
		let pval = params.pval;//参数值
		if (isNotBlank(r)) {
			redirect = '/' + r;
			otherQueryParams[pname] = pval;
		}
	}

	const paramsString = window.location.search.substring(1);
	let urlParamMap = parseUrlParams(paramsString);
	var token = (query.token as LocationQueryValue) ?? urlParamMap['token'];
	otherQueryParams.token = token;


	if (isBlank(redirect)) {
		redirect = '/dashboard'
	}

	return {
		redirect: decodeURI(redirect),
		params: otherQueryParams
	}
}


onMounted(() => {


	let params = getParams();


	// 获取参数字符串（去除问号）

	const redirect = params.redirect
	var token = params.params.token;
	const authCode = params.params.authCode;


	if (isBlank(token)) {
		token = authCode;
	}


	if (isNotBlank(token)) {
		if (isWxCp() && isMobile()) {
			//说明是手机端 微信跳转回来的
			//企微 并且是手机
			let params = route.params;
			let r = params.r;//跳转地址

			let q = params.pval;//参数值
			let pname = params.pname;//参数key

			let href = `http://qwh5.flyflow.cc/#/redirectlogin/${r}/${pname}/${q}?token=` + token;

			window.location.href = href;
			return
		}
		userStore
					.loginByToken(token)
					.then(() => {
						return userStore.getInfo();
					})
					.then(({roles}) => {
						return permissionStore.generateRoutes(roles);
					})
					.then(() => {
						router.push({path: redirect, query: params.params});
					});
	} else {
		handleLogin();

	}


})

</script>

<style lang="less" scoped>
.login-container {
	position: fixed;
	top: 0;
	left: 0;
	width: 100vw;
	height: 100vh;
	background: linear-gradient(135deg, #0a0e27 0%, #112240 100%);
	display: flex;
	align-items: center;
	justify-content: center;
	overflow: hidden;

	&::before {
		content: '';
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-image: 
			linear-gradient(rgba(0, 212, 255, 0.1) 1px, transparent 1px),
			linear-gradient(90deg, rgba(0, 212, 255, 0.1) 1px, transparent 1px);
		background-size: 50px 50px;
		animation: moveGrid 20s linear infinite;
	}

	&::after {
		content: '';
		position: absolute;
		top: -50%;
		left: -50%;
		width: 200%;
		height: 200%;
		background: radial-gradient(circle, rgba(0, 212, 255, 0.1) 0%, transparent 70%);
	animation: pulse 4s ease-in-out infinite alternate;
	}

	.login-content {
		position: relative;
		z-index: 1;
		text-align: center;
		padding: 40px;
		background: rgba(17, 34, 64, 0.8);
		border-radius: 16px;
		border: 1px solid rgba(0, 212, 255, 0.3);
		box-shadow: 0 0 30px rgba(0, 212, 255, 0.3);

		.logo {
			font-size: 48px;
			font-weight: bold;
			color: #00d4ff;
			text-shadow: 0 0 10px rgba(0, 212, 255, 0.8);
			margin-bottom: 20px;
		}

		.title {
			font-size: 24px;
			color: #8892b0;
			margin-bottom: 30px;
		}

		.loading {
			display: inline-block;
			width: 40px;
			height: 40px;
			border: 3px solid rgba(0, 212, 255, 0.3);
			border-top-color: #00d4ff;
			border-radius: 50%;
			animation: spin 1s linear infinite;
		}
	}
}

@keyframes moveGrid {
	0% {
		background-position: 0 0;
	}
	100% {
		background-position: 50px 50px;
	}
}

@keyframes pulse {
	0% {
		transform: scale(1);
		opacity: 0.3;
	}
	100% {
		transform: scale(1.2);
		opacity: 0.1;
	}
}

@keyframes spin {
	to {
		transform: rotate(360deg);
	}
}
</style>
