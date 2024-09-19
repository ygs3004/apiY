const utils = {
    required: (value) => {
        return !!value || "값을 입력해주세요"
    },
    isEmail: (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email) || "이메일 형식이 아닙니다.";
    }
}

export default utils;