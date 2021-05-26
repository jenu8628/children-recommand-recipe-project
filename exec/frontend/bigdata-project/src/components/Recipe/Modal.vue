<template>
    <transition name="modal">
        <div class="modal-mask">
          <div class="modal-wrapper">
            <div class="modal-container">


              <!-- modal header -->
              <div class="modal-header">
                <slot name="header">
                  default header
                </slot>
              </div>


              <!-- modal body -->
              <div class="modal-body">
                <slot name="body">
                  default body
                </slot>
              </div>


              <!-- modal footer -->
              <div class="modal-footer">
                <slot name="footer">
                  default footer
                </slot>
              </div>
            </div>
          </div>
        </div>
      </transition>
</template>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0%;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align:middle;
}

.modal-container {
  width: 60%;
  height: 60%;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: white;
  overflow-y: auto;
  border-radius: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-header {
  width:100%;
  position: relative;
  height: 13%;
}

.modal-header h3 {
  position: absolute;
  left:50%;
  margin: 0 auto;
  color: #222;
  transform:translateX(-50%);
}

.modal-header i{
  position: absolute;
  right: 2%;
  color: #EFB730;
  font-size: 1.3rem;
}

.modal-body {
  margin: 20px 0;
}

.modal-default-button {
  float: right;
}

.name_of_gallery input {
  width: 90%;
  border: 0;
  padding: 10px 12px;
  margin: 10px;
  border-bottom: 2px solid #dfdfdf;
  background-color: transparent;
  font-size: 17px;
}


.desc_of_gallery textarea {
  resize: none;
  height: 10rem;
  width: 90%;
  border: 0;
  padding: 10px 12px;
  border: 2px solid #dfdfdf;
  background-color: transparent;
  font-size: 17px;
  margin: 16px 0 0;
}

.name_of_gallery input:focus {
  outline: none;
  border-bottom: 2px solid red;

}

.desc_of_gallery textarea:focus {
  outline: none;
  border: 2px solid red;
}

.modal-footer{
  width:100%;
  display: flex;
  justify-content: center;
  align-items: flex-end;
  
}

.modal-footer button {
  font-size: 1rem;
  padding: 0.4rem;
  margin-right:2rem;
  color: white;
  background: #EFB730;
  border-radius: 0.2rem;
  border: 2px solid #EFB730;
}

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

</style>