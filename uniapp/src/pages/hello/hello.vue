<template>
	<canvas style="width: 300px; height: 200px; background-color: wheat;" canvas-id="canvas"></canvas>
</template>
  
<script setup lang="ts">
import { ref, onMounted } from 'vue';

onMounted(() => {
	const ctx = uni.createCanvasContext("canvas", this);
	console.log(ctx);
	draw(ctx);
});
const draw = (ctx: UniApp.CanvasContext)=>{
ctx.rect(50, 50, 200, 100)
  ctx.setFillStyle('red')
  ctx.fill()
  ctx.draw()	
}
</script>
  
<style scoped>
</style>
  