import Vue from 'vue'
import App from './App.vue'

import router from './router'
import ImageUploader from 'vue-image-upload-resize'
import InfiniteLoading from 'vue-infinite-loading';

Vue.use(InfiniteLoading, { /* options */ });
Vue.use(ImageUploader);

Vue.config.productionTip = false


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
