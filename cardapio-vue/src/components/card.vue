<script setup lang="ts">
const props = defineProps<{
  id: string;
  title: string;
  image: string;
  price: number;
}>();

const handleEditClick = () => {
  const customEvent = new CustomEvent("edit-food", {
    detail: {
      id: props.id,
      title: props.title,
      image: props.image,
      price: props.price,
    },
  });
  window.dispatchEvent(customEvent);
};
</script>

<template>
  <div class="card">
    <img :src="image" alt="lanche do burguer king" />
    <h1>{{ title }}</h1>
    <h2>{{ price.toFixed(2) }}</h2>
    <button @click="handleEditClick">Editar</button>
  </div>
</template>

<style scoped>
.card {
  display: flex;
  flex-direction: column;
  height: 20rem;
  width: 15rem;
  padding: 2rem;
  background-color: #333;
  border-radius: 1rem;
  box-shadow: 1px 1px 1rem solid black;
}

h1 {
  font-size: 2rem;
}

img {
  object-fit: contain;
  width: auto;
  max-height: 40%;
}
</style>
