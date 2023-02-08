import './app.sass'
import Header from './components/sections/Header/Header';
import Footer from './components/sections/footer/Footer';
import { Navigate, Route, Routes } from "solid-app-router";
import MainPage from './components/pages/MainPage';
import AuthPage from './components/pages/AuthPage';
import { createSignal } from 'solid-js';
import { createEffect, For } from "solid-js"

// http://178.20.42.226/

async function postData(url = '', data = {}) {
  // Default options are marked with *
  const response = await fetch(`http://178.20.42.226/${url}`, {
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, *cors, same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'include', // include, *same-origin, omit
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify(data) // body data type must match "Content-Type" header
  })
  return response.json(); // parses JSON response into native JavaScript objects
}
async function postForm(url = '', formData) {
  // Default options are marked with *
  const response = await fetch(`http://178.20.42.226/${url}`, {
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, *cors, same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'include', // include, *same-origin, omit
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: formData// body data type must match "Content-Type" header
  })
  return response.json(); // parses JSON response into native JavaScript objects
}

function App() {
  return (
    <div class="app">
      <div class="app__window" aos-data="fade-in" data-aos-duration="900">
        {/* <Header /> */}
        <main className="main">
          <Routes>
            <Route path="/" element={<Navigate href="/auth"/>}/>
            <Route path="/auth" element={<AuthPage />} />
          </Routes>
        </main>
        {/* <Footer /> */}
      </div>
    </div>
  )
}

export {postData, postForm}
export default App;