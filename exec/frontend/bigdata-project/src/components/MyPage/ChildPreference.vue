<template>
 <div class="childpreference-container">
    <div id="preference">
        <div>
            육류
        </div>
        <div>
            야채류
        </div>
        <div>
            재료3
        </div>
        <div>
            재료4

        </div>
        <div>재료5</div>
        <div>재료6</div>
    </div>
    <div id="button">
        <button>등록</button>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
.childpreference-container {
  overflow: hidden;
  display: flex;
  width: 100vw;
  height: 100vh;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  
}
.childpreference-container #preference {
  display: grid;
  width: 80%;
  height: 60%;
  
  margin: 1rem;
  padding: 0.2rem;
  grid-template-columns: 1fr 1fr ;
  grid-template-rows: 1fr 1fr 1fr;
  row-gap: 1rem;
  column-gap:1rem;
  text-align: center;
  
}
.childpreference-container #preference div {
  border-radius: 50%;
  border:1px solid black
}
.childpreference-container #button {
  display: flex;
  width: 70%;
  height: 10%;
  justify-content: center;
  align-items: center;
  
}
</style>