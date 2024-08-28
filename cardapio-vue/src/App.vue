<script setup lang="ts">
import { ref, onMounted } from "vue";
import Card from "./components/card.vue";
import axios from "axios";

interface Food {
  id: string;
  title: string;
  image: string;
  price: number;
}

const foods = ref<Food[]>([]);
const selectedFood = ref<Food | null>(null);
const modal = ref<HTMLDialogElement | null>(null);

onMounted(async () => {
  try {
    const response = await axios.get("http://localhost:8080/food");
    foods.value = response.data;
  } catch (error) {
    console.error("Erro ao buscar foods:", error);
  }

  window.addEventListener("edit-food", (event: any) => {
    selectedFood.value = event.detail;
    modal.value?.showModal();
  });
});

const closeModal = () => {
  modal.value?.close();
  selectedFood.value = null;
};

const submitEdit = () => {
  if (selectedFood.value) {
    axios
      .put(
        `http://localhost:8080/food/${selectedFood.value.id}`,
        selectedFood.value
      )
      .then(() => {
        console.log("Food updated successfully");
        closeModal();
      })
      .catch((error) => console.error("Erro ao editar food:", error));
  }
};
</script>

<template>
  <body>
    <div>
      <h1>Cardápio do BK 👑</h1>
    </div>

    <div class="grid" v-if="foods.length > 0">
      <Card
        v-for="food in foods"
        :key="food.id"
        :id="food.id"
        :title="food.title"
        :image="food.image"
        :price="food.price"
      />
    </div>
    <div v-else>
      <p>Carregando...</p>
    </div>

    <dialog ref="modal" v-if="selectedFood">
      <div class="modal-boby">
        <h3>Editar Lanche</h3>
        <form @submit.prevent="submitEdit">
          <label for="title" id="title">Nome</label>
          <input v-model="selectedFood.title" title="Nome" id="title" />
          <label for="image">URL da imagem</label>
          <input v-model="selectedFood.image" id="image" type="text" />
          <label for="price">Preço</label>
          <input v-model.number="selectedFood.price" id="price" type="number" />
          <div class="form-buttons">
            <button type="button" @click="closeModal">Cancelar</button>
            <button type="submit">Salvar</button>
          </div>
        </form>
      </div>
    </dialog>
  </body>
</template>

<style scoped>
body {
  display: flex;
  flex-direction: column;
  height: auto;
  width: 100%;
}

.grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: start;
  align-items: start;
  gap: 2rem;
  width: 80%;
}

dialog {
  border: none;
  padding: 3rem;
  border-radius: 8px;
}

.modal-body {
  display: flex;
  flex-direction: column;
}

dialog::backdrop {
  background-color: rgba(0, 0, 0, 0.5);
}

dialog[data-modal] {
  max-width: 300px;
  margin: auto;
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
  width: 40vw;
}

label {
  text-align: start;
}

input {
  margin-bottom: 1rem;
}

.form-buttons {
  display: flex;
  flex-direction: row;
  justify-content: end;
  gap: 1rem;
  width: 100%;
}
</style>
