import './assets/main.css'

import { createApp } from 'vue'
import App from '@/App.vue'
import router from '@/router'
import axios, {HttpStatusCode} from "axios";
import vuetify from "@/plugins/vuetify.js"
import {useModal} from "@/components/CustomModal.vue"
import utils from "@/utils/utils.js";

const app = createApp(App)

const globalAxios = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    timeout: 5000,
    withCredentials: true,
    headers: {
        Accept: 'application/json',
        "Content-Type": 'application/json'
    },
});

globalAxios.interceptors.request.use(config => {
    const loginUser = utils.getSessionStorageItem("loginUser");

    if (loginUser) {
        const {tokenType, accessToken} = loginUser;
        config.headers.Authorization = `${tokenType} ${accessToken}`;
    }

    return config;
});

globalAxios.interceptors.response.use(
    (response) => response,
    (error) => {
        console.warn(error);
        if(error.response?.status === HttpStatusCode.Unauthorized){
            localStorage.removeItem("loginUser")
            if(location.href.endsWith("/login")){
                router.push("/login")
            }
        }

        if(error.response.data.message){
            useModal().showModal({
                content: error.response.data.message
            })
        }

        return error.response;
    },
);

app.config.globalProperties.$axios = globalAxios;
app.config.globalProperties.$utils = utils;

app.config.globalProperties.$modal = useModal().showModal;

app.use(router)
app.use(vuetify)
app.mount('#app')
