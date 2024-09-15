<script setup>
import {ref} from "vue";

const emit = defineEmits(['close'])
const isActive = ref(true);
const close = () => {
  isActive.value = false;
  emit('close');
}

</script>

<template>
  <VDialog max-width="500" v-model="isActive">
    <VCard title="Dialog">
      <VCardText>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
        magna aliqua.
      </VCardText>

      <VCardActions>
        <VSpacer></VSpacer>

        <VBtn
            text="Close Dialog"
            @click="close"
        ></VBtn>
      </VCardActions>
    </VCard>
  </VDialog>
</template>

<script>
import { createApp, h } from 'vue';
import Modal from './CustomModal.vue';
import vueify from "@/plugins/vueify.js";

export const useModal = () => {
  const showModal = (content) => {
    const container = document.createElement('div');

    const modalApp = createApp({
      render() {
        return h(Modal, {
          onClose: () => {
            setTimeout(() => {
              modalApp.unmount();
              document.body.removeChild(container);
            }, 300); // 애니메이션 효과를 위해 살짝 지연
          },
        }, content);
      },
    });

    modalApp.use(vueify);
    document.body.appendChild(container);
    modalApp.mount(container);
  };

  return { showModal };
}
</script>

