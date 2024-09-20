const utils = {
    required: (value) => {
        return !!value || "값을 입력해주세요"
    },
    isEmail: (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email) || "이메일 형식이 아닙니다.";
    },

    setLocalStorageItem: (key, value) => {
        localStorage.setItem(key, JSON.stringify(value))
    },

    getLocalStorageItem: (key) => {
        return JSON.parse(localStorage.getItem(key))
    },

    removeLocalStorageItem: (key) => {
        localStorage.removeItem(key);
    },

    setSessionStorageItem: (key, value) => {
        sessionStorage.setItem(key, JSON.stringify(value))
    },

    getSessionStorageItem: (key) => {
        return JSON.parse(sessionStorage.getItem(key))
    },

    removeSessionStorageItem: (key) => {
        sessionStorage.removeItem(key);
    }
}

export default utils;