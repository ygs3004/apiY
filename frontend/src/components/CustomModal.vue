<script setup>
import {ref} from "vue";

const emit = defineEmits(['close'])
defineProps({
  title: {
    type: String,
    default: "알림",
  },
  content: {
    type: String,
    default: "확인필요",
  },
})

const isActive = ref(true);
const close = () => {
  isActive.value = false;
  emit('close');
}

</script>

<template>
  <VDialog max-width="500" v-model="isActive">
    <VCard :title="title">
      <VCardText>
        {{content}}
      </VCardText>

      <VCardActions>
        <VSpacer></VSpacer>

        <VBtn
            text="닫기"
            @click="close"
        ></VBtn>
      </VCardActions>
    </VCard>
  </VDialog>
</template>

<script>
import { createApp, h } from 'vue';
import Modal from './CustomModal.vue';
import vuetify from "@/plugins/vuetify.js";

export const useModal = () => {
  const showModal = (props = {}, slotContent) => {
    const container = document.createElement('div');

    const modalApp = createApp({
      render() {
        return h(Modal, {
          ...props,
          onClose: () => {
            setTimeout(() => {
              modalApp.unmount();
              document.body.removeChild(container);
            }, 300); // 애니메이션 효과를 위해 살짝 지연
          },
        }, slotContent);
      },
    });

    modalApp.use(vuetify);
    document.body.appendChild(container);
    modalApp.mount(container);
  };

  return { showModal };
}
</script>

