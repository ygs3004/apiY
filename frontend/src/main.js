import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";

const app = createApp(App)

app.use(router)

console.log(import.meta.env.VITE_API_URL);

app.config.globalProperties.$axios = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    withCredentials: true,
    headers: {
        Accept: 'application/json',
        "Content-Type": 'application/json'
    }
});

console.log(app.config.globalProperties.$axios);

app.mount('#app')
