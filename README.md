<a href="#see-it-in-action">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="docs/banner-dark.svg" />
    <source media="(prefers-color-scheme: light)" srcset="docs/banner-light.svg" />
    <img src="docs/banner-light.svg" alt="Lumière Cinema: pick a film, choose seats on a live map, book in one click" width="100%" />
  </picture>
</a>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java 17+" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot 3.2" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/JavaScript-ES6-F7DF1E?style=flat-square&logo=javascript&logoColor=black" alt="JavaScript ES6" />
  <img src="https://img.shields.io/badge/Vite-6-646CFF?style=flat-square&logo=vite&logoColor=white" alt="Vite 6" />
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white" alt="HTML5" />
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css&logoColor=white" alt="CSS3" />
  <img src="https://img.shields.io/badge/Jackson-2F2F2F?style=flat-square&logo=json&logoColor=white" alt="Jackson" />
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-MIT-F59E0B?style=flat-square" alt="MIT License" /></a>
</p>

<p align="center">
  <a href="#see-it-in-action"><b>Demo</b></a> &nbsp;·&nbsp;
  <a href="#highlights"><b>Highlights</b></a> &nbsp;·&nbsp;
  <a href="#screenshots"><b>Screenshots</b></a> &nbsp;·&nbsp;
  <a href="#how-it-works"><b>How it works</b></a> &nbsp;·&nbsp;
  <a href="#getting-started"><b>Getting started</b></a> &nbsp;·&nbsp;
  <a href="#api-endpoints"><b>API</b></a>
</p>

> [!NOTE]
> **Portfolio / university project.** Data is stored in JSON files and the admin login (`admin` / `admin`) is a demo, so keep it on `localhost`. See [Known limitations](#known-limitations).

**Lumière Cinema** is a full-stack cinema booking system. Customers browse the repertoire, pick a screening and choose seats on a live seat map; the booking is confirmed with a reservation number. Behind a separate login, an admin panel manages films, screenings and reservations and shows an audit log of everything that happened.

The backend is a **Spring Boot REST API** with a classic controller → service → repository structure. Every booking is validated on the server (seat range, duplicates, double booking) and a new screening is rejected if it **overlaps another one in the same room**, counting the film's length plus a 15-minute buffer. Data is persisted to **JSON files with atomic writes**, so the app runs with no database to install. The frontend is **plain JavaScript (ES modules) served by Vite**, in a dark, gold-accented "premium cinema" theme.

The name is a nod to the **Lumière brothers**, pioneers of cinema; the visual style was inspired by the game **[Expedition 33](https://expedition33.com/)**.

<p align="center"><b>Keywords</b></p>

<table align="center">
  <tr>
    <td align="right" valign="middle"><sub><b>Backend</b></sub></td>
    <td>
      <img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java" />
      <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot" />
      <img src="https://img.shields.io/badge/Spring%20Web%20MVC-6DB33F?style=flat-square&logo=spring&logoColor=white" alt="Spring Web MVC" />
      <img src="https://img.shields.io/badge/Jackson-2F2F2F?style=flat-square&logo=json&logoColor=white" alt="Jackson" />
      <img src="https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven" />
    </td>
  </tr>
  <tr>
    <td align="right" valign="middle"><sub><b>Frontend</b></sub></td>
    <td>
      <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black" alt="JavaScript" />
      <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white" alt="HTML5" />
      <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css&logoColor=white" alt="CSS3" />
      <img src="https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=vite&logoColor=white" alt="Vite" />
      <img src="https://img.shields.io/badge/Vanilla%20JS-B45309?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJtMTggMTYgNC00LTQtNCIgLz4gPHBhdGggZD0ibTYgOC00IDQgNCA0IiAvPiA8cGF0aCBkPSJtMTQuNSA0LTUgMTYiIC8%2BIDwvc3ZnPg%3D%3D" alt="Vanilla JS" />
    </td>
  </tr>
  <tr>
    <td align="right" valign="middle"><sub><b>Architecture</b></sub></td>
    <td>
      <img src="https://img.shields.io/badge/REST%20API-334155?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cmVjdCB4PSIxNiIgeT0iMTYiIHdpZHRoPSI2IiBoZWlnaHQ9IjYiIHJ4PSIxIiAvPiA8cmVjdCB4PSIyIiB5PSIxNiIgd2lkdGg9IjYiIGhlaWdodD0iNiIgcng9IjEiIC8%2BIDxyZWN0IHg9IjkiIHk9IjIiIHdpZHRoPSI2IiBoZWlnaHQ9IjYiIHJ4PSIxIiAvPiA8cGF0aCBkPSJNNSAxNnYtM2ExIDEgMCAwIDEgMS0xaDEyYTEgMSAwIDAgMSAxIDF2MyIgLz4gPHBhdGggZD0iTTEyIDEyVjgiIC8%2BIDwvc3ZnPg%3D%3D" alt="REST API" />
      <img src="https://img.shields.io/badge/Layered%20Architecture-334155?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJNMTIuODMgMi4xOGEyIDIgMCAwIDAtMS42NiAwTDIuNiA2LjA4YTEgMSAwIDAgMCAwIDEuODNsOC41OCAzLjkxYTIgMiAwIDAgMCAxLjY2IDBsOC41OC0zLjlhMSAxIDAgMCAwIDAtMS44M3oiIC8%2BIDxwYXRoIGQ9Ik0yIDEyYTEgMSAwIDAgMCAuNTguOTFsOC42IDMuOTFhMiAyIDAgMCAwIDEuNjUgMGw4LjU4LTMuOUExIDEgMCAwIDAgMjIgMTIiIC8%2BIDxwYXRoIGQ9Ik0yIDE3YTEgMSAwIDAgMCAuNTguOTFsOC42IDMuOTFhMiAyIDAgMCAwIDEuNjUgMGw4LjU4LTMuOUExIDEgMCAwIDAgMjIgMTciIC8%2BIDwvc3ZnPg%3D%3D" alt="Layered Architecture" />
      <img src="https://img.shields.io/badge/Dependency%20Injection-334155?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJNOCAzSDdhMiAyIDAgMCAwLTIgMnY1YTIgMiAwIDAgMS0yIDIgMiAyIDAgMCAxIDIgMnY1YzAgMS4xLjkgMiAyIDJoMSIgLz4gPHBhdGggZD0iTTE2IDIxaDFhMiAyIDAgMCAwIDItMnYtNWMwLTEuMS45LTIgMi0yYTIgMiAwIDAgMS0yLTJWNWEyIDIgMCAwIDAtMi0yaC0xIiAvPiA8L3N2Zz4%3D" alt="Dependency Injection" />
      <img src="https://img.shields.io/badge/JSON%20Persistence-334155?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJNNiAyMmEyIDIgMCAwIDEtMi0yVjRhMiAyIDAgMCAxIDItMmg4YTIuNCAyLjQgMCAwIDEgMS43MDQuNzA2bDMuNTg4IDMuNTg4QTIuNCAyLjQgMCAwIDEgMjAgOHYxMmEyIDIgMCAwIDEtMiAyeiIgLz4gPHBhdGggZD0iTTE0IDJ2NWExIDEgMCAwIDAgMSAxaDUiIC8%2BIDxwYXRoIGQ9Ik0xMCAxMmExIDEgMCAwIDAtMSAxdjFhMSAxIDAgMCAxLTEgMSAxIDEgMCAwIDEgMSAxdjFhMSAxIDAgMCAwIDEgMSIgLz4gPHBhdGggZD0iTTE0IDE4YTEgMSAwIDAgMCAxLTF2LTFhMSAxIDAgMCAxIDEtMSAxIDEgMCAwIDEtMS0xdi0xYTEgMSAwIDAgMC0xLTEiIC8%2BIDwvc3ZnPg%3D%3D" alt="JSON Persistence" />
      <img src="https://img.shields.io/badge/Input%20Validation-334155?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJNMjAgMTNjMCA1LTMuNSA3LjUtNy42NiA4Ljk1YTEgMSAwIDAgMS0uNjctLjAxQzcuNSAyMC41IDQgMTggNCAxM1Y2YTEgMSAwIDAgMSAxLTFjMiAwIDQuNS0xLjIgNi4yNC0yLjcyYTEuMTcgMS4xNyAwIDAgMSAxLjUyIDBDMTQuNTEgMy44MSAxNyA1IDE5IDVhMSAxIDAgMCAxIDEgMXoiIC8%2BIDxwYXRoIGQ9Im05IDEyIDIgMiA0LTQiIC8%2BIDwvc3ZnPg%3D%3D" alt="Input Validation" />
    </td>
  </tr>
  <tr>
    <td align="right" valign="middle"><sub><b>Domain</b></sub></td>
    <td>
      <img src="https://img.shields.io/badge/Cinema%20Booking-B45309?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJNMiA5YTMgMyAwIDAgMSAwIDZ2MmEyIDIgMCAwIDAgMiAyaDE2YTIgMiAwIDAgMCAyLTJ2LTJhMyAzIDAgMCAxIDAtNlY3YTIgMiAwIDAgMC0yLTJINGEyIDIgMCAwIDAtMiAyWiIgLz4gPHBhdGggZD0iTTEzIDV2MiIgLz4gPHBhdGggZD0iTTEzIDE3djIiIC8%2BIDxwYXRoIGQ9Ik0xMyAxMXYyIiAvPiA8L3N2Zz4%3D" alt="Cinema Booking" />
      <img src="https://img.shields.io/badge/Seat%20Reservation-B45309?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJNMTkgOVY2YTIgMiAwIDAgMC0yLTJIN2EyIDIgMCAwIDAtMiAydjMiIC8%2BIDxwYXRoIGQ9Ik0zIDE2YTIgMiAwIDAgMCAyIDJoMTRhMiAyIDAgMCAwIDItMnYtNWEyIDIgMCAwIDAtNCAwdjEuNWEuNS41IDAgMCAxLS41LjVoLTlhLjUuNSAwIDAgMS0uNS0uNVYxMWEyIDIgMCAwIDAtNCAweiIgLz4gPHBhdGggZD0iTTUgMTh2MiIgLz4gPHBhdGggZD0iTTE5IDE4djIiIC8%2BIDwvc3ZnPg%3D%3D" alt="Seat Reservation" />
      <img src="https://img.shields.io/badge/Scheduling-B45309?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJNMTYgMTR2Mi4ybDEuNiAxIiAvPiA8cGF0aCBkPSJNMTYgMnYzIiAvPiA8cGF0aCBkPSJNMjEgNy4zMzhWNWEyIDIgMCAwMC0yLTJINWEyIDIgMCAwMC0yIDJ2MTRhMiAyIDAgMDAyIDJoMi4zMzgiIC8%2BIDxwYXRoIGQ9Ik0zIDloNS44NTkiIC8%2BIDxwYXRoIGQ9Ik04IDJ2MyIgLz4gPGNpcmNsZSBjeD0iMTYiIGN5PSIxNiIgcj0iNiIgLz4gPC9zdmc%2B" alt="Scheduling" />
      <img src="https://img.shields.io/badge/Admin%20Panel%20/%20CRUD-B45309?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cmVjdCB3aWR0aD0iNyIgaGVpZ2h0PSI5IiB4PSIzIiB5PSIzIiByeD0iMSIgLz4gPHJlY3Qgd2lkdGg9IjciIGhlaWdodD0iNSIgeD0iMTQiIHk9IjMiIHJ4PSIxIiAvPiA8cmVjdCB3aWR0aD0iNyIgaGVpZ2h0PSI5IiB4PSIxNCIgeT0iMTIiIHJ4PSIxIiAvPiA8cmVjdCB3aWR0aD0iNyIgaGVpZ2h0PSI1IiB4PSIzIiB5PSIxNiIgcng9IjEiIC8%2BIDwvc3ZnPg%3D%3D" alt="Admin Panel / CRUD" />
    </td>
  </tr>
  <tr>
    <td align="right" valign="middle"><sub><b>UI&nbsp;/&nbsp;UX</b></sub></td>
    <td>
      <img src="https://img.shields.io/badge/UI%20Design-7C3AED?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJNMTIgMjJhMSAxIDAgMCAxIDAtMjAgMTAgOSAwIDAgMSAxMCA5IDUgNSAwIDAgMS01IDVoLTIuMjVhMS43NSAxLjc1IDAgMCAwLTEuNCAyLjhsLjMuNGExLjc1IDEuNzUgMCAwIDEtMS40IDIuOHoiIC8%2BIDxjaXJjbGUgY3g9IjEzLjUiIGN5PSI2LjUiIHI9Ii41IiBmaWxsPSJjdXJyZW50Q29sb3IiIC8%2BIDxjaXJjbGUgY3g9IjE3LjUiIGN5PSIxMC41IiByPSIuNSIgZmlsbD0iY3VycmVudENvbG9yIiAvPiA8Y2lyY2xlIGN4PSI2LjUiIGN5PSIxMi41IiByPSIuNSIgZmlsbD0iY3VycmVudENvbG9yIiAvPiA8Y2lyY2xlIGN4PSI4LjUiIGN5PSI3LjUiIHI9Ii41IiBmaWxsPSJjdXJyZW50Q29sb3IiIC8%2BIDwvc3ZnPg%3D%3D" alt="UI Design" />
      <img src="https://img.shields.io/badge/Responsive%20Design-7C3AED?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cmVjdCB3aWR0aD0iMTQiIGhlaWdodD0iMjAiIHg9IjUiIHk9IjIiIHJ4PSIyIiByeT0iMiIgLz4gPHBhdGggZD0iTTEyIDE4aC4wMSIgLz4gPC9zdmc%2B" alt="Responsive Design" />
      <img src="https://img.shields.io/badge/CSS%20Animations-7C3AED?style=flat-square&logo=data%3Aimage%2Fsvg%2Bxml%3Bbase64%2CPHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IndoaXRlIiBzdHJva2Utd2lkdGg9IjIiIHN0cm9rZS1saW5lY2FwPSJyb3VuZCIgc3Ryb2tlLWxpbmVqb2luPSJyb3VuZCIgPiA8cGF0aCBkPSJNMTEuMDE3IDIuODE0YTEgMSAwIDAgMSAxLjk2NiAwbDEuMDUxIDUuNTU4YTIgMiAwIDAgMCAxLjU5NCAxLjU5NGw1LjU1OCAxLjA1MWExIDEgMCAwIDEgMCAxLjk2NmwtNS41NTggMS4wNTFhMiAyIDAgMCAwLTEuNTk0IDEuNTk0bC0xLjA1MSA1LjU1OGExIDEgMCAwIDEtMS45NjYgMGwtMS4wNTEtNS41NThhMiAyIDAgMCAwLTEuNTk0LTEuNTk0bC01LjU1OC0xLjA1MWExIDEgMCAwIDEgMC0xLjk2Nmw1LjU1OC0xLjA1MWEyIDIgMCAwIDAgMS41OTQtMS41OTR6IiAvPiA8cGF0aCBkPSJNMjAgMnY0IiAvPiA8cGF0aCBkPSJNMjIgNGgtNCIgLz4gPGNpcmNsZSBjeD0iNCIgY3k9IjIwIiByPSIyIiAvPiA8L3N2Zz4%3D" alt="CSS Animations" />
    </td>
  </tr>
</table>

## See it in action

<p align="center">
  <img src="docs/demo.gif" alt="Demo: landing page, movie grid, screenings for Interstellar, seat selection and booking confirmation" width="100%" />
</p>

<p align="center"><sub>Landing page → <em>Teraz gramy</em> → Interstellar → pick a screening → seats 47 and 48 (taken seats can't be clicked) → <em>Zarezerwuj</em> → confirmation #12.</sub></p>

<details>
<summary><b>Admin panel in action</b> (login, rejected overlapping screening, audit log)</summary>
<br />

<p align="center">
  <img src="docs/admin.gif" alt="Admin demo: login, schedule a screening that overlaps Interstellar and gets rejected, schedule it on a free evening, check the log" width="100%" />
</p>

<p align="center"><sub>Login → <em>Seanse</em> → Oppenheimer at 22:00 in Sala 1 is rejected (Interstellar runs until ~00:04) → a free evening works → <em>Logi</em>.</sub></p>

</details>

## Highlights

<table>
  <tr>
    <td width="33%" valign="top">
      <img src="docs/icons/clapperboard.svg" width="44" alt="" /><br />
      <b>Cinematic UI</b><br />
      <sub>Full-screen hero with spotlights and film strips, poster cards with a golden glow, Playfair Display + DM Sans, smooth entrance animations.</sub>
    </td>
    <td width="33%" valign="top">
      <img src="docs/icons/armchair.svg" width="44" alt="" /><br />
      <b>Live seat map</b><br />
      <sub>Seat grid sized to the room, availability loaded from the API, click to select, running summary and a button that counts your tickets.</sub>
    </td>
    <td width="33%" valign="top">
      <img src="docs/icons/shield-check.svg" width="44" alt="" /><br />
      <b>Server-side validation</b><br />
      <sub>Screening must exist, seats must be in range, no duplicates and no double booking. Clear Polish error messages end up in a toast.</sub>
    </td>
  </tr>
  <tr>
    <td width="33%" valign="top">
      <img src="docs/icons/calendar-clock.svg" width="44" alt="" /><br />
      <b>No double-booked rooms</b><br />
      <sub>A new screening is rejected if it overlaps another one in the same room (film length + 15 min buffer). Screenings in the past are rejected too.</sub>
    </td>
    <td width="33%" valign="top">
      <img src="docs/icons/file-json.svg" width="44" alt="" /><br />
      <b>No database needed</b><br />
      <sub>Films, screenings and reservations live in JSON files. Each save writes a <code>.tmp</code> file and moves it into place, and a corrupted file is set aside instead of crashing the app.</sub>
    </td>
    <td width="33%" valign="top">
      <img src="docs/icons/layout-dashboard.svg" width="44" alt="" /><br />
      <b>Admin panel</b><br />
      <sub>Four tabs: films (add / delete), screenings (schedule / delete), reservations (cancel) and logs. Customers never see admin forms.</sub>
    </td>
  </tr>
  <tr>
    <td width="33%" valign="top">
      <img src="docs/icons/scroll-text.svg" width="44" alt="" /><br />
      <b>Audit log</b><br />
      <sub>Every change (new film, screening, reservation, cancellation) is written with a timestamp to <code>log.txt</code> and readable in the admin panel.</sub>
    </td>
    <td width="33%" valign="top">
      <img src="docs/icons/smartphone.svg" width="44" alt="" /><br />
      <b>Responsive</b><br />
      <sub>Works on desktop and phones, with a hamburger menu and a two-column poster grid on small screens.</sub>
    </td>
    <td width="33%" valign="top">
      <img src="docs/icons/rocket.svg" width="44" alt="" /><br />
      <b>Runs out of the box</b><br />
      <sub>Maven Wrapper included, and on first start the <code>DataSeeder</code> creates 2 rooms, 8 films and 30 screenings over the next 5 days.</sub>
    </td>
  </tr>
</table>

## Screenshots

<p align="center">
  <img src="docs/hero.png" alt="Desktop movie grid and the mobile landing page" width="100%" />
</p>

<table>
  <tr>
    <td width="50%" valign="top">
      <img src="docs/screenshots/03-screenings.png" alt="Screenings for a film" />
      <p align="center"><b>Pick a screening</b><br /><sub>Upcoming showtimes for the film, sorted by date, with room and capacity.</sub></p>
    </td>
    <td width="50%" valign="top">
      <img src="docs/screenshots/04-seats.png" alt="Seat selection" />
      <p align="center"><b>Choose your seats</b><br /><sub>Taken seats are dimmed; the summary and the button update as you click.</sub></p>
    </td>
  </tr>
  <tr>
    <td width="50%" valign="top">
      <img src="docs/screenshots/05-confirmation.png" alt="Booking confirmation" />
      <p align="center"><b>Booking confirmed</b><br /><sub>Film, room, date, seats and the reservation number.</sub></p>
    </td>
    <td width="50%" valign="top">
      <img src="docs/screenshots/07-admin-screenings.png" alt="Admin: scheduling conflict" />
      <p align="center"><b>Scheduling with conflict detection</b><br /><sub>The backend refuses a screening that would overlap Interstellar in Sala 1.</sub></p>
    </td>
  </tr>
</table>

<details>
<summary><b>More screens</b> (landing page, admin tabs, login, mobile)</summary>
<br />

| Landing page | Repertoire |
|:---:|:---:|
| <img src="docs/screenshots/01-hero.png" alt="Landing page" /> | <img src="docs/screenshots/02-repertoire.png" alt="Movie grid" /> |
| **Admin: films** | **Admin: reservations** |
| <img src="docs/screenshots/06-admin-movies.png" alt="Admin films" /> | <img src="docs/screenshots/08-admin-reservations.png" alt="Admin reservations" /> |
| **Admin: audit log** | **Admin login** |
| <img src="docs/screenshots/09-admin-logs.png" alt="Admin logs" /> | <img src="docs/screenshots/10-login.png" alt="Admin login" /> |

<p align="center"><img src="docs/mobile.png" alt="Mobile: landing page, movie grid, screenings" width="100%" /></p>

</details>

<sub>Screenshots and GIFs come from the real app (Spring Boot backend + Vite frontend) running locally with the seeded data.</sub>

## How it works

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/pipeline-dark.svg" />
  <source media="(prefers-color-scheme: light)" srcset="docs/pipeline-light.svg" />
  <img src="docs/pipeline-light.svg" alt="Animation: POST /api/reservations/19 with seats [47, 48] passes four server-side checks, returns 200, is written atomically to reservations.json and logged" width="100%" />
</picture>

### User flow

```
Repertoire  →  Pick a film  →  See screenings  →  Select seats  →  Booking confirmation
```

The customer only sees the movie grid, the screening list and the seat picker. All management happens in the admin panel behind a separate login.

### One booking

```mermaid
sequenceDiagram
    autonumber
    actor U as Customer
    participant FE as app.js
    participant C as ReservationController
    participant S as ReservationService
    participant R as Repositories
    participant L as LoggingService

    U->>FE: seats 47, 48 → "Zarezerwuj"
    FE->>C: POST /api/reservations/19 [47, 48]<br/>(Vite proxy → :8080)
    C->>S: createReservation(19, [47, 48])
    S->>R: find screening #35;19
    R-->>S: Interstellar · Sala 1 · 60 seats
    S->>S: range 1..60 · no duplicates · not taken
    alt a check fails
        S-->>C: exception with Polish message
        C-->>FE: 400 "Miejsca już zajęte: [25]"
        FE-->>U: red toast
    else all good
        S->>R: save → reservations.json (tmp + move)
        S->>L: "Rezerwacja #35;12 na seans #35;19 ..."
        C-->>FE: 200 Reservation #35;12
        FE-->>U: confirmation card
    end
```

### Architecture

```mermaid
flowchart LR
    subgraph FE["Frontend · Vite + Vanilla JS"]
        UI["index.html<br/>app.js · styles.css"]
    end
    subgraph BE["Backend · Spring Boot :8080"]
        C["Controllers<br/>movies · screenings · reservations<br/>rooms · login / logs"]
        S["Services<br/>validation · conflict detection"]
        R["Repositories"]
        SEED["DataSeeder<br/>(first start)"]
        LOG["LoggingService"]
    end
    J[("movies.json<br/>screenings.json<br/>reservations.json")]
    M[("rooms<br/>(in memory)")]
    T[("log.txt")]

    UI -- "fetch /api/*<br/>(Vite proxy)" --> C
    C --> S
    S --> R
    S --> LOG
    R --> J
    R --> M
    LOG --> T
    SEED --> R
```

### Domain model

```mermaid
classDiagram
    direction LR
    class Movie {
        Long id
        String title
        int durationMinutes
    }
    class Room {
        Long id
        String name
        int seats
    }
    class Screening {
        Long id
        LocalDateTime dateTime
    }
    class Reservation {
        Long id
        List~Integer~ seats
    }
    Screening --> Movie
    Screening --> Room
    Reservation --> Screening
```

Seats are numbered `1..room.seats`. A screening "occupies" its room from `dateTime` until `dateTime + durationMinutes + 15 min`.

### Room conflict detection

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/conflict-dark.svg" />
  <source media="(prefers-color-scheme: light)" srcset="docs/conflict-light.svg" />
  <img src="docs/conflict-light.svg" alt="Timeline of Sala 1: Oppenheimer at 22:00 overlaps Interstellar and is rejected; Inception at 11:00 fits before Dune and is accepted" width="100%" />
</picture>

`ScreeningService.createScreening` loads the film and the room, then compares the new time slot with every screening in the same room. Two slots overlap when `newStart < existingEnd && newEnd > existingStart`. On a conflict the API answers `400` with the name of the colliding film and its time range, which the admin panel shows as a toast.

### Try the API from a terminal

<p align="center">
  <img src="docs/terminal.svg" alt="Animated terminal: backend starts with seeded data, one successful booking, then 400 responses for a taken seat, a seat out of range, duplicates and an overlapping screening" width="100%" />
</p>

<sub>All responses above are real output of the running backend.</sub>

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17+, Spring Boot 3.2 (`spring-boot-starter-web`), embedded Tomcat |
| Serialization | Jackson (`jackson-databind`, `jackson-datatype-jsr310` for `LocalDateTime`) |
| Persistence | JSON files (`movies.json`, `screenings.json`, `reservations.json`), atomic write via temp file + move |
| Build | Maven (wrapper included, no install needed) |
| Frontend | HTML5, CSS3 (custom properties, animations), vanilla JavaScript (ES modules), Fetch API |
| Dev server | Vite 6 (dev server + `/api` proxy to `:8080`) |
| Fonts | Playfair Display, DM Sans, JetBrains Mono (Google Fonts) |

## Project Structure

```
Cinema_Booking_System/
├── backend/
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd             # Maven Wrapper
│   └── src/
│       ├── cinema/
│       │   ├── CinemaApplication.java
│       │   ├── CorsConfig.java     # CORS for the Vite dev server
│       │   ├── controller/         # REST endpoints (/api/...)
│       │   ├── model/              # Movie, Room, Screening, Reservation
│       │   ├── repository/         # JSON-file repos, DataSeeder
│       │   └── service/            # validation, conflicts, logging
│       └── resources/
│           └── application.properties
├── frontend/
│   ├── index.html                  # all views in one page
│   ├── vite.config.js              # port 5178, /api proxy
│   └── src/
│       ├── css/styles.css          # "Lumière" theme
│       └── js/app.js               # views, API calls, seat map, admin
├── docs/                           # README images, GIFs, SVGs
├── LICENSE
└── README.md
```

## Getting Started

### Prerequisites

- **Java 17** or newer
- **Node.js 18+** and npm
- Maven is **not** required, the project ships the Maven Wrapper

### Backend

```bash
cd backend
.\mvnw.cmd spring-boot:run      # Windows
./mvnw spring-boot:run          # Linux / macOS
```

The API starts at `http://localhost:8080`. Data files (`movies.json`, `screenings.json`, `reservations.json`, `log.txt`) are created in the directory you start the backend from.

> [!TIP]
> **Fresh data with current dates.** The seeder only runs when the files are empty or missing, and screenings in the past are hidden. To get 30 screenings starting today, stop the backend, delete `movies.json`, `screenings.json` and `reservations.json` from `backend/`, and start it again.

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5178`. Requests to `/api/*` are proxied to the backend, so there is nothing else to configure.

### Default credentials

| Username | Password |
|----------|----------|
| `admin` | `admin` |

## Seeded Data

On first start `DataSeeder` creates:

| Entity | Count | Details |
|--------|:-----:|---------|
| Rooms | 2 | Sala 1 (60 seats), VIP (30 seats) |
| Films | 8 | Oppenheimer (180 min), Dune: Part Two (166), Interstellar (169), The Batman (176), Spider-Man: Across the Spider-Verse (140), Gladiator II (148), Inception (148), Joker: Folie à Deux (138) |
| Screenings | 30 | Today and the next 4 days, 4 to 8 per day, in both rooms |

## API Endpoints

<details>
<summary><b>All endpoints</b></summary>

Errors are returned as `400 Bad Request` with a plain-text Polish message (shown in the UI as a toast), missing resources as `404`.

### Movies
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/movies` | List all films |
| `GET` | `/api/movies/{id}` | Get a film |
| `POST` | `/api/movies` | Add a film `{"title": "...", "durationMinutes": 120}` |
| `PUT` | `/api/movies/{id}` | Update title and/or duration |
| `DELETE` | `/api/movies/{id}` | Delete a film |

### Screenings
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/screenings` | List all screenings |
| `GET` | `/api/screenings/{id}` | Get a screening |
| `GET` | `/api/screenings/movie/{movieId}` | Upcoming screenings of a film, sorted by date |
| `GET` | `/api/screenings/{id}/availability` | Free seat numbers |
| `POST` | `/api/screenings` | Create `{"movie":{"id":1},"room":{"id":1},"dateTime":"2026-10-11T22:00"}` (checks the past and room conflicts) |
| `DELETE` | `/api/screenings/{id}` | Delete a screening |

### Reservations
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/reservations` | List all reservations |
| `GET` | `/api/reservations/{screeningId}` | Reservations for a screening |
| `GET` | `/api/reservations/{screeningId}/available` | Free seat numbers (same as `availability`) |
| `POST` | `/api/reservations/{screeningId}` | Book seats, body: `[47, 48]` |
| `DELETE` | `/api/reservations/{id}` | Cancel a reservation |

### Rooms
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/rooms` | List rooms |
| `POST` | `/api/rooms` | Add a room `{"name": "...", "seats": 40}` (kept in memory only) |

### Admin / misc
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/login` | Check credentials `{"username","password"}` (SHA-256), returns `"OK"` or `401` |
| `GET` | `/api/logs` | Contents of `log.txt` |
| `GET` | `/api/test` | Health check |

</details>

## Configuration

<details>
<summary><b>Ports and settings</b></summary>

| Setting | Where | Default |
|---------|-------|---------|
| Backend port | `backend/src/resources/application.properties` → `server.port` | `8080` |
| Frontend port | `frontend/vite.config.js` → `server.port` | `5178` |
| API proxy | `frontend/vite.config.js` → `server.proxy['/api']` | `http://localhost:8080` |
| CORS origins | `CorsConfig.java` | `localhost` / `127.0.0.1` on `5178` and `5179` |
| Screening buffer | `ScreeningService.createScreening` | 15 min after each film |
| Log file | `LoggingService.LOG_FILE` | `log.txt` |

</details>

## Known limitations

<details>
<summary><b>What a production version would change</b></summary>
<br />

- **Authentication is a demo.** The admin login is checked in the browser (SHA-256 of the password) and the admin endpoints have no auth, so anyone who can reach the API can change data. `POST /api/login` exists on the backend but the UI does not use it yet. A real version would use Spring Security with sessions or JWT.
- **Rooms live in memory.** They are recreated by the seeder on every start; rooms added with `POST /api/rooms` disappear after a restart.
- **JSON files, single process.** Repositories keep data in lists and rewrite the whole file on each change, with no locking. Good for a demo, not for concurrent users; a database with a unique constraint on (screening, seat) would be the next step.
- **Deleting a film** does not remove its screenings or reservations.
- **Small phones:** the 60-seat grid of Sala 1 is wider than a 390 px screen, so the seat page scrolls sideways there.

</details>

## Acknowledgments

- Partially built using **vibe coding**, an AI-assisted development approach where parts of the codebase were generated and iterated on through natural-language conversation.
- Fonts: [Playfair Display](https://fonts.google.com/specimen/Playfair+Display), [DM Sans](https://fonts.google.com/specimen/DM+Sans), [JetBrains Mono](https://fonts.google.com/specimen/JetBrains+Mono)
- Icons in this README: [Lucide](https://lucide.dev) (ISC license)

## License

This project is available under the [MIT License](LICENSE).

## Contact

**Grzegorz Sienkiewicz**
✉️ gsienkiewicz03@gmail.com
🐙 GitHub: [@Nurek54](https://github.com/Nurek54)
