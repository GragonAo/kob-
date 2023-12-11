<template>
  <view class="play-ground">
    <GameMap/>
  </view>
</template>

<script lang="ts" setup>
import GameMap from '@/pages/components/GameMap.vue';
</script>

<style scoped>
.play-ground{
  margin-top: 10px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>