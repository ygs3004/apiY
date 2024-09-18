import './assets/main.css'

import { createApp } from 'vue'
import App from '@/App.vue'
import router from '@/router'
import axios from "axios";
import vuetify from "@/plugins/vuetify.js"
import {useModal} from "@/components/CustomModal.vue"

const app = createApp(App)

const globalAxios = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    withCredentials: true,
    headers: {
        Accept: 'application/json',
        "Content-Type": 'application/json'
    },
});

globalAxios.interceptors.response.use(
    (response) => response,
    (error) => {
        useModal().showModal({
            title: "오류",
            content: error.message
        })
    }
)

app.config.globalProperties.$axios = globalAxios;

app.config.globalProperties.$modal = useModal().showModal;

app.use(router)
app.use(vuetify)
app.mount('#app')
