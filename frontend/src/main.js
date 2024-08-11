import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";
import vuetify from "./plugins/vueify.js"

const app = createApp(App)

app.config.globalProperties.$axios = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    withCredentials: true,
    headers: {
        Accept: 'application/json',
        "Content-Type": 'application/json'
    }
});

app.use(router)
app.use(vuetify)
app.mount('#app')
