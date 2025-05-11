/* ========= SEKCJE ========= */
const moviesSec  = document.getElementById('movies-section');
const screensSec = document.getElementById('screenings-section');
const reserveSec = document.getElementById('reserve-section');
const adminSec   = document.getElementById('admin-section');

/* ========= przyciski navbar ========= */
const btnFilms   = document.getElementById('btn-films');
const btnScreens = document.getElementById('btn-screenings');
const btnReserve = document.getElementById('btn-reserve');
const btnAdmin   = document.getElementById('btn-admin');

/* ========= panel admin – zakładki ========= */
const tabRes = document.getElementById('tab-reservations');
const tabLog = document.getElementById('tab-logs');
const adminResDiv = document.getElementById('admin-reservations');
const adminLogsDiv= document.getElementById('admin-logs');

/* ========= pola formularzy ========= */
const titleInput = document.getElementById('title');
const durInput   = document.getElementById('duration');
const scMovieSel = document.getElementById('sc-movie');
const scRoomSel  = document.getElementById('sc-room');
const scDateInp  = document.getElementById('sc-date');
const addStatus  = document.getElementById('add-status');
const scStatus   = document.getElementById('sc-status');
const resStatus  = document.getElementById('res-status');
const resScreenSel = document.getElementById('res-screen');
const seatGrid   = document.getElementById('seat-grid');
const btnBook    = document.getElementById('btn-book');
const btnCancel  = document.getElementById('btn-cancel');

let curScreenId=null, roomSeats=0, taken=[], selected=new Set();

/* ========= NAV ========= */
function show(view){
    moviesSec .hidden = view!=='movies';
    screensSec.hidden = view!=='screens';
    reserveSec.hidden = view!=='reserve';
    adminSec  .hidden = view!=='admin';

    if(view==='movies')   loadMovies();
    if(view==='screens')  loadScreenings();
    if(view==='reserve')  prepareReserveTab();
    if(view==='admin')    loadAdminReservations();
}
btnFilms  .onclick = ()=>show('movies');
btnScreens.onclick = ()=>show('screens');
btnReserve.onclick = ()=>show('reserve');
btnAdmin  .onclick = ()=>show('admin');

/* ========= FILMY ========= */
async function loadMovies(){
    const movies=await fetch('/api/movies').then(r=>r.json());
    document.getElementById('movies').innerHTML = movies.length
        ? `<ul>${movies.map(m=>`
        <li>${m.id}. <strong>${m.title}</strong> – ${m.durationMinutes} min
            <button class="small del-film" data-id="${m.id}">🗑️</button>
        </li>`).join('')}</ul>`
        : '<p>Brak filmów.</p>';

    scMovieSel.innerHTML='<option hidden value=\"\">Film</option>'+
        movies.map(m=>`<option value=\"${m.id}\">${m.title}</option>`).join('');
}
document.getElementById('movies').addEventListener('click',async e=>{
    if(!e.target.classList.contains('del-film')) return;
    await fetch('/api/movies/'+e.target.dataset.id,{method:'DELETE'});
    loadMovies(); loadScreenings();
});
document.getElementById('movie-form').onsubmit=async e=>{
    e.preventDefault();
    await fetch('/api/movies',{method:'POST',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({title:titleInput.value,durationMinutes:+durInput.value})});
    e.target.reset(); loadMovies();
};

/* ========= SALE ========= */
async function loadRooms(){
    const rooms=await fetch('/api/rooms').then(r=>r.json());
    scRoomSel.innerHTML='<option hidden value=\"\">Sala</option>'+
        rooms.map(r=>`<option value=\"${r.id}\">${r.name} (${r.seats})</option>`).join('');
}

/* ========= SEANSE ========= */
async function loadScreenings(){
    const list=await fetch('/api/screenings').then(r=>r.json());
    document.getElementById('screenings').innerHTML=list.length
        ? `<ul>${list.map(s=>`
        <li>${s.id}. <strong>${s.movie.title}</strong>, sala <em>${s.room.name}</em>,
            ${s.dateTime.replace('T',' ')}
            <button class="small reserve" data-id="${s.id}" data-seats="${s.room.seats}">🎟️</button>
            <button class="small del-screen" data-id="${s.id}">🗑️</button>
        </li>`).join('')}</ul>`
        : '<p>Brak seansów.</p>';

    resScreenSel.innerHTML='<option hidden value=\"\">– wybierz seans –</option>'+
        list.map(s=>`<option value=\"${s.id}\" data-seats=\"${s.room.seats}\">`+
            `${s.movie.title} | ${s.room.name} | ${s.dateTime.replace('T',' ')}`+
            `</option>`).join('');
}
document.getElementById('screenings').addEventListener('click',async e=>{
    const id=e.target.dataset.id;
    if(e.target.classList.contains('del-screen')){
        await fetch('/api/screenings/'+id,{method:'DELETE'}); loadScreenings();
    }
    if(e.target.classList.contains('reserve')){
        resScreenSel.value=id;
        showReserveFor(id,+e.target.dataset.seats);
        show('reserve');
    }
});
document.getElementById('screening-form').onsubmit=async e=>{
    e.preventDefault();
    await fetch('/api/screenings',{method:'POST',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({movie:{id:+scMovieSel.value},room:{id:+scRoomSel.value},dateTime:scDateInp.value})});
    e.target.reset(); loadScreenings();
};

/* ========= REZERWACJA ========= */
function prepareReserveTab(){
    if(!resScreenSel.value){ seatGrid.innerHTML=''; btnBook.disabled=true; }
}
resScreenSel.onchange=()=>{
    const opt=resScreenSel.selectedOptions[0];
    if(opt) showReserveFor(opt.value,+opt.dataset.seats);
};
async function showReserveFor(screenId,seats){
    curScreenId=screenId; roomSeats=seats;
    const free=await fetch(`/api/screenings/${screenId}/availability`).then(r=>r.json());
    taken=[...Array(seats).keys()].map(i=>i+1).filter(n=>!free.includes(n));
    selected.clear(); btnBook.disabled=true; resStatus.textContent='';
    drawGrid();
    document.getElementById('reserve-heading').textContent='Seans #'+screenId;
}
function drawGrid(){
    seatGrid.style.gridTemplateColumns='repeat(10,32px)';
    seatGrid.innerHTML='';
    for(let n=1;n<=roomSeats;n++){
        const d=document.createElement('div');
        d.textContent=n; d.dataset.seat=n;
        d.className='seat '+(taken.includes(n)?'taken':'free');
        seatGrid.appendChild(d);
    }
}
seatGrid.onclick=e=>{
    if(!e.target.classList.contains('seat')||e.target.classList.contains('taken'))return;
    const n=+e.target.dataset.seat;
    if(selected.has(n)){selected.delete(n);e.target.classList.remove('selected');}
    else{selected.add(n);e.target.classList.add('selected');}
    btnBook.disabled=!selected.size;
};
btnCancel.onclick=()=>show('screens');
btnBook.onclick=async ()=>{
    const r=await fetch('/api/reservations/'+curScreenId,{method:'POST',
        headers:{'Content-Type':'application/json'},body:JSON.stringify([...selected])});
    if(r.ok){resStatus.textContent='✔ Zarezerwowano';loadScreenings();setTimeout(()=>show('screens'),700);}
    else    resStatus.textContent=await r.text();
};

/* ========= PANEL ADMINA ========= */
async function loadAdminReservations(){
    const res=await fetch('/api/reservations').then(r=>r.json());
    adminResDiv.innerHTML=res.length
        ? `<table><thead><tr><th>ID</th><th>Seans</th><th>Miejsca</th><th></th></tr></thead><tbody>`+
        res.map(r=>`
        <tr>
          <td>${r.id}</td><td>${r.screening.id}</td><td>${r.seats.join(', ')}</td>
          <td><button class=\"small del-res\" data-id=\"${r.id}\">🗑️</button></td>
        </tr>`).join('')+
        `</tbody></table>`
        : '<p>Brak rezerwacji.</p>';
}
adminResDiv.addEventListener('click',async e=>{
    if(!e.target.classList.contains('del-res')) return;
    if(confirm('Usunąć rezerwację #'+e.target.dataset.id+'?')){
        await fetch('/api/reservations/'+e.target.dataset.id,{method:'DELETE'});
        loadAdminReservations(); loadScreenings();
    }
});
function switchAdminTab(tab){
    tabRes.classList.toggle('active',tab==='res');
    tabLog.classList.toggle('active',tab==='log');
    adminResDiv.hidden = tab!=='res';
    adminLogsDiv.hidden= tab!=='log';
}
tabRes.onclick=()=>switchAdminTab('res');
tabLog.onclick=()=>switchAdminTab('log');

/* ========= INIT ========= */
loadRooms();
show('movies');
