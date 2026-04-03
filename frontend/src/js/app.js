/* ================================================================
   LUMIÈRE CINEMA — Application Logic (Full Rebuild)

   Flow:  Repertuar → Film → Seanse → Rezerwacja → Potwierdzenie
   Admin: Login → Filmy / Seanse / Rezerwacje / Logi
   ================================================================ */

// ── State ──
let adminLogged = false;
let currentMovieId = null;
let currentScreening = null;
let roomSeats = 0;
let takenSeats = [];
let selectedSeats = new Set();

// ── API helper ──
async function api(url, options = {}) {
    try {
        const res = await fetch(url, options);
        return res;
    } catch (e) {
        showToast('Błąd połączenia z serwerem', 'error');
        return null;
    }
}

async function apiJson(url) {
    const res = await api(url);
    if (!res || !res.ok) return [];
    return res.json();
}

// ── Toast ──
function showToast(message, type = 'success') {
    document.querySelectorAll('.toast').forEach(t => t.remove());
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;
    document.body.appendChild(toast);
    setTimeout(() => toast.remove(), 3200);
}

// ── Helpers ──
function esc(str) {
    if (!str) return '';
    const d = document.createElement('div');
    d.textContent = str;
    return d.innerHTML;
}

function fmtDate(dt) {
    if (!dt) return '';
    const d = new Date(dt);
    if (isNaN(d)) return dt.replace('T', ' ');
    const day = d.toLocaleDateString('pl-PL', { weekday: 'long', day: 'numeric', month: 'long' });
    const time = d.toLocaleTimeString('pl-PL', { hour: '2-digit', minute: '2-digit' });
    return day + ', ' + time;
}

function fmtDateShort(dt) {
    if (!dt) return '';
    const d = new Date(dt);
    if (isNaN(d)) return dt.replace('T', ' ');
    return d.toLocaleDateString('pl-PL', { day: 'numeric', month: 'short' }) +
        ' ' + d.toLocaleTimeString('pl-PL', { hour: '2-digit', minute: '2-digit' });
}

function seatWord(n) {
    if (n === 1) return 'miejsce';
    if (n >= 2 && n <= 4) return 'miejsca';
    return 'miejsc';
}

// ══════════════════════════════════════════════════
//  NAVIGATION
// ══════════════════════════════════════════════════

const sections = [
    'repertoire-section', 'movie-detail-section', 'reserve-section',
    'confirmation-section', 'admin-login-section', 'admin-section'
];

function show(view) {
    sections.forEach(id => {
        document.getElementById(id).hidden = true;
    });

    // Nav active state
    document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));

    switch (view) {
        case 'repertoire':
            document.getElementById('repertoire-section').hidden = false;
            document.getElementById('btn-repertoire').classList.add('active');
            loadRepertoire();
            break;

        case 'movie-detail':
            document.getElementById('movie-detail-section').hidden = false;
            document.getElementById('btn-repertoire').classList.add('active');
            break;

        case 'reserve':
            document.getElementById('reserve-section').hidden = false;
            document.getElementById('btn-repertoire').classList.add('active');
            break;

        case 'confirmation':
            document.getElementById('confirmation-section').hidden = false;
            document.getElementById('btn-repertoire').classList.add('active');
            break;

        case 'admin':
            document.getElementById('btn-admin').classList.add('active');
            if (adminLogged) {
                document.getElementById('admin-section').hidden = false;
                loadAdminData();
            } else {
                document.getElementById('admin-login-section').hidden = false;
            }
            break;
    }

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Nav buttons
document.getElementById('btn-repertoire').onclick = () => show('repertoire');
document.getElementById('btn-admin').onclick = () => show('admin');

// Back buttons
document.getElementById('btn-back-repertoire').onclick = () => show('repertoire');
document.getElementById('btn-back-movie').onclick = () => openMovie(currentMovieId);

// Mobile menu
const hamburger = document.getElementById('hamburger');
const mobileMenu = document.getElementById('mobile-menu');
hamburger.onclick = () => mobileMenu.classList.toggle('open');

document.querySelectorAll('.mobile-nav-btn').forEach(btn => {
    btn.onclick = () => {
        show(btn.dataset.view);
        mobileMenu.classList.remove('open');
    };
});

// Navbar scroll
window.addEventListener('scroll', () => {
    document.getElementById('navbar').classList.toggle('scrolled', window.scrollY > 20);
});

// ══════════════════════════════════════════════════
//  1. REPERTOIRE — Movie Grid (public)
// ══════════════════════════════════════════════════

async function loadRepertoire() {
    const movies = await apiJson('/api/movies');
    const grid = document.getElementById('movies-grid');

    // Update hero stat
    const statEl = document.getElementById('stat-movies');
    if (statEl) statEl.textContent = movies.length;

    if (!movies.length) {
        grid.innerHTML = `
      <div class="movies-empty">
        <div class="movies-empty-icon">🎬</div>
        <p>Brak filmów w repertuarze</p>
        <p style="font-size:0.85rem;margin-top:0.5rem;color:var(--text-muted)">Wkrótce dodamy nowe tytuły!</p>
      </div>`;
        return;
    }

    // Color palette for poster backgrounds
    const gradients = [
        'linear-gradient(135deg, #1a0a2e 0%, #16213e 50%, #0a0f1a 100%)',
        'linear-gradient(135deg, #1a0000 0%, #2d1b00 50%, #0a0a0f 100%)',
        'linear-gradient(135deg, #001a1a 0%, #0d2137 50%, #0a0f1a 100%)',
        'linear-gradient(135deg, #1a1a00 0%, #2d1b00 50%, #0f0a00 100%)',
        'linear-gradient(135deg, #0d001a 0%, #1a0a2e 50%, #0a0a0f 100%)',
        'linear-gradient(135deg, #001a0d 0%, #0a2e1a 50%, #0a0f0a 100%)',
        'linear-gradient(135deg, #1a0a0a 0%, #2e1a1a 50%, #0f0a0a 100%)',
        'linear-gradient(135deg, #0a0a1a 0%, #1a1a2e 50%, #0a0a0f 100%)',
    ];

    const icons = ['🎬', '🎥', '🎞️', '🎭', '🌟', '🎪', '🎻', '🎹'];

    grid.innerHTML = movies.map((m, i) => `
    <div class="movie-card" style="animation-delay:${i * 0.08}s" onclick="openMovie(${m.id})" role="button" tabindex="0">
      <div class="movie-card__glow"></div>
      <div class="movie-card__poster">
        <div class="movie-card__poster-bg" style="background:${gradients[i % gradients.length]}"></div>
        <span class="movie-card__poster-icon">${icons[i % icons.length]}</span>
      </div>
      <div class="movie-card__overlay"></div>
      <div class="movie-card__content">
        <span class="movie-card__duration">🕐 ${m.durationMinutes} min</span>
        <div class="movie-card__title">${esc(m.title)}</div>
        <div class="movie-card__cta">
          Zobacz seanse <span class="movie-card__cta-arrow">→</span>
        </div>
      </div>
    </div>`).join('');
}

// ══════════════════════════════════════════════════
//  2. MOVIE DETAIL — Screenings for a movie
// ══════════════════════════════════════════════════

async function openMovie(movieId) {
    currentMovieId = movieId;

    // Load movie info
    const res = await api(`/api/movies/${movieId}`);
    if (!res || !res.ok) { show('repertoire'); return; }
    const movie = await res.json();

    // Hero
    document.getElementById('movie-hero').innerHTML = `
    <div class="movie-detail-hero">
      <div class="movie-detail-poster"><span>🎬</span></div>
      <div class="movie-detail-info">
        <h1 class="movie-detail-title">${esc(movie.title)}</h1>
        <div class="movie-detail-meta">
          <span class="meta-badge">🕐 ${movie.durationMinutes} min</span>
        </div>
      </div>
    </div>`;

    // Load screenings for this movie
    const screenings = await apiJson(`/api/screenings/movie/${movieId}`);
    const list = document.getElementById('movie-screenings');

    if (!screenings.length) {
        list.innerHTML = `
      <div class="screenings-empty">
        <p>Brak zaplanowanych seansów dla tego filmu</p>
        <p style="font-size:0.85rem;margin-top:0.5rem;color:var(--text-muted)">Sprawdź ponownie później</p>
      </div>`;
    } else {
        list.innerHTML = screenings.map((s, i) => `
      <div class="screening-card" style="animation-delay:${i * 0.06}s">
        <div class="screening-info-left">
          <div class="screening-datetime">
            <span class="screening-date">${fmtDate(s.dateTime)}</span>
          </div>
        </div>
        <div class="screening-details">
          <span class="screening-detail">
            <span class="screening-detail-icon">◈</span> ${esc(s.room.name)}
          </span>
          <span class="screening-detail">
            <span class="screening-detail-icon">💺</span> ${s.room.seats} miejsc
          </span>
        </div>
        <div class="screening-actions">
          <button class="btn-primary btn-reserve-screening"
                  data-id="${s.id}"
                  data-movie="${esc(movie.title)}"
                  data-room="${esc(s.room.name)}"
                  data-seats="${s.room.seats}"
                  data-datetime="${s.dateTime}">
            🎟️ Rezerwuj
          </button>
        </div>
      </div>`).join('');
    }

    show('movie-detail');
}

// Delegate click on screening reserve buttons
document.getElementById('movie-screenings').addEventListener('click', e => {
    const btn = e.target.closest('.btn-reserve-screening');
    if (!btn) return;
    openReservation({
        id: +btn.dataset.id,
        movieTitle: btn.dataset.movie,
        roomName: btn.dataset.room,
        totalSeats: +btn.dataset.seats,
        dateTime: btn.dataset.datetime
    });
});

// ══════════════════════════════════════════════════
//  3. RESERVATION — Seat Selection
// ══════════════════════════════════════════════════

async function openReservation(screening) {
    currentScreening = screening;
    roomSeats = screening.totalSeats;
    selectedSeats.clear();

    // Header
    document.getElementById('reserve-heading').textContent = esc(screening.movieTitle);
    document.getElementById('reserve-subtitle').textContent =
        `${fmtDate(screening.dateTime)} • ${screening.roomName}`;

    // Sidebar
    document.getElementById('reserve-sidebar').innerHTML = `
    <h3 style="font-family:var(--font-display);font-size:1.1rem;margin-bottom:1rem;">Informacje o seansie</h3>
    <div class="info-row"><span class="info-label">Film</span><span>${esc(screening.movieTitle)}</span></div>
    <div class="info-row"><span class="info-label">Sala</span><span>${esc(screening.roomName)}</span></div>
    <div class="info-row"><span class="info-label">Termin</span><span>${fmtDateShort(screening.dateTime)}</span></div>
    <div class="info-row"><span class="info-label">Miejsc w sali</span><span>${screening.totalSeats}</span></div>
    <div class="info-row" id="sidebar-available"><span class="info-label">Wolnych</span><span>…</span></div>`;

    // Load availability
    const available = await apiJson(`/api/screenings/${screening.id}/availability`);
    const allSeats = Array.from({ length: roomSeats }, (_, i) => i + 1);
    takenSeats = allSeats.filter(n => !available.includes(n));

    // Update sidebar
    document.getElementById('sidebar-available').innerHTML =
        `<span class="info-label">Wolnych</span><span>${available.length}</span>`;

    // Draw grid
    drawSeatGrid();
    updateBookButton();
    document.getElementById('reserve-summary').hidden = true;

    show('reserve');
}

function drawSeatGrid() {
    const grid = document.getElementById('seat-grid');
    const cols = roomSeats <= 20 ? 5 : roomSeats <= 30 ? 6 : roomSeats <= 60 ? 10 : 12;
    grid.style.gridTemplateColumns = `repeat(${cols}, 34px)`;
    grid.innerHTML = '';

    for (let n = 1; n <= roomSeats; n++) {
        const seat = document.createElement('div');
        seat.textContent = n;
        seat.dataset.seat = n;

        if (takenSeats.includes(n)) {
            seat.className = 'seat taken';
            seat.title = `Miejsce ${n} — zajęte`;
        } else {
            seat.className = 'seat free';
            seat.title = `Miejsce ${n} — kliknij aby wybrać`;
        }
        grid.appendChild(seat);
    }
}

// Seat click handler
document.getElementById('seat-grid').onclick = e => {
    const seat = e.target.closest('.seat');
    if (!seat || seat.classList.contains('taken')) return;

    const n = +seat.dataset.seat;
    if (selectedSeats.has(n)) {
        selectedSeats.delete(n);
        seat.className = 'seat free';
    } else {
        selectedSeats.add(n);
        seat.className = 'seat selected';
    }
    updateBookButton();
};

function updateBookButton() {
    const btn = document.getElementById('btn-book');
    const summary = document.getElementById('reserve-summary');
    const count = selectedSeats.size;

    btn.disabled = count === 0;

    if (count > 0) {
        const sorted = [...selectedSeats].sort((a, b) => a - b);
        btn.textContent = `🎟️ Zarezerwuj ${count} ${seatWord(count)}`;
        document.getElementById('summary-seats-list').textContent = sorted.join(', ');
        document.getElementById('summary-count').textContent = count;
        summary.hidden = false;
    } else {
        btn.textContent = '🎟️ Wybierz miejsca';
        summary.hidden = true;
    }
}

// Book button
document.getElementById('btn-book').onclick = async () => {
    const btn = document.getElementById('btn-book');
    btn.disabled = true;
    btn.textContent = '⏳ Rezerwowanie...';

    const res = await api(`/api/reservations/${currentScreening.id}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify([...selectedSeats])
    });

    if (res && res.ok) {
        const data = await res.json();
        showConfirmation(data);
    } else {
        const msg = res ? await res.text() : 'Błąd połączenia';
        showToast(msg, 'error');
        btn.disabled = false;
        btn.textContent = `🎟️ Zarezerwuj ${selectedSeats.size} ${seatWord(selectedSeats.size)}`;
    }
};

// ══════════════════════════════════════════════════
//  4. CONFIRMATION
// ══════════════════════════════════════════════════

function showConfirmation(reservation) {
    const sorted = [...reservation.seats].sort((a, b) => a - b);
    document.getElementById('confirmation-details').innerHTML = `
    <div class="confirmation-row"><span>Film</span><strong>${esc(currentScreening.movieTitle)}</strong></div>
    <div class="confirmation-row"><span>Sala</span><strong>${esc(currentScreening.roomName)}</strong></div>
    <div class="confirmation-row"><span>Termin</span><strong>${fmtDate(currentScreening.dateTime)}</strong></div>
    <div class="confirmation-row"><span>Miejsca</span><strong>${sorted.join(', ')}</strong></div>
    <div class="confirmation-row"><span>Nr rezerwacji</span><strong>#${reservation.id}</strong></div>`;
    show('confirmation');
    showToast('Rezerwacja potwierdzona!');
}

// ══════════════════════════════════════════════════
//  5. ADMIN LOGIN
// ══════════════════════════════════════════════════

document.getElementById('admin-login-form').onsubmit = async e => {
    e.preventDefault();
    const login = document.getElementById('admin-user').value;
    const pass = document.getElementById('admin-pass').value;

    const hashBuffer = await crypto.subtle.digest('SHA-256', new TextEncoder().encode(pass));
    const hex = Array.from(new Uint8Array(hashBuffer))
        .map(b => b.toString(16).padStart(2, '0')).join('');

    if (login === 'admin' && hex === '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918') {
        adminLogged = true;
        showToast('Zalogowano jako administrator');
        show('admin');
    } else {
        document.getElementById('admin-login-status').textContent = '❌ Niepoprawny login lub hasło';
        document.getElementById('admin-login-status').style.color = '#f87171';
    }
};

// ══════════════════════════════════════════════════
//  6. ADMIN PANEL
// ══════════════════════════════════════════════════

// ── Tab switching ──
document.querySelectorAll('.admin-tab').forEach(tab => {
    tab.onclick = () => {
        document.querySelectorAll('.admin-tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.admin-panel').forEach(p => p.hidden = true);
        tab.classList.add('active');
        document.getElementById(tab.dataset.tab).hidden = false;

        if (tab.dataset.tab === 'admin-movies') loadAdminMovies();
        if (tab.dataset.tab === 'admin-screenings') loadAdminScreenings();
        if (tab.dataset.tab === 'admin-reservations') loadAdminReservations();
        if (tab.dataset.tab === 'admin-logs') loadAdminLogs();
    };
});

function loadAdminData() {
    loadAdminMovies();
    loadAdminDropdowns();
}

// ── Admin: Movies ──
async function loadAdminMovies() {
    const movies = await apiJson('/api/movies');
    const el = document.getElementById('admin-movies-list');

    if (!movies.length) {
        el.innerHTML = '<div class="screenings-empty"><p>Brak filmów</p></div>';
        return;
    }

    el.innerHTML = `<table>
    <thead><tr><th>ID</th><th>Tytuł</th><th>Czas</th><th>Akcje</th></tr></thead>
    <tbody>${movies.map(m => `
      <tr>
        <td style="font-family:var(--font-mono);color:var(--text-muted)">#${m.id}</td>
        <td><strong>${esc(m.title)}</strong></td>
        <td>${m.durationMinutes} min</td>
        <td><button class="btn-small btn-danger del-movie" data-id="${m.id}">Usuń</button></td>
      </tr>`).join('')}
    </tbody></table>`;
}

document.getElementById('admin-movies-list').addEventListener('click', async e => {
    const btn = e.target.closest('.del-movie');
    if (!btn) return;
    if (!confirm('Usunąć film #' + btn.dataset.id + '? Powiązane seanse mogą przestać działać.')) return;
    await api('/api/movies/' + btn.dataset.id, { method: 'DELETE' });
    showToast('Film usunięty');
    loadAdminMovies();
    loadAdminDropdowns();
});

document.getElementById('movie-form').onsubmit = async e => {
    e.preventDefault();
    const title = document.getElementById('title').value.trim();
    const dur = +document.getElementById('duration').value;

    const res = await api('/api/movies', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title, durationMinutes: dur })
    });

    if (res && res.ok) {
        showToast(`Film "${title}" dodany`);
        e.target.reset();
        loadAdminMovies();
        loadAdminDropdowns();
    } else {
        const msg = res ? await res.text() : 'Błąd';
        showToast(msg, 'error');
    }
};

// ── Admin: Screenings ──
async function loadAdminDropdowns() {
    const movies = await apiJson('/api/movies');
    const rooms = await apiJson('/api/rooms');

    document.getElementById('sc-movie').innerHTML =
        '<option hidden value="">— Wybierz film —</option>' +
        movies.map(m => `<option value="${m.id}">${esc(m.title)}</option>`).join('');

    document.getElementById('sc-room').innerHTML =
        '<option hidden value="">— Wybierz salę —</option>' +
        rooms.map(r => `<option value="${r.id}">${esc(r.name)} (${r.seats} miejsc)</option>`).join('');
}

async function loadAdminScreenings() {
    const list = await apiJson('/api/screenings');
    const el = document.getElementById('admin-screenings-list');

    if (!list.length) {
        el.innerHTML = '<div class="screenings-empty"><p>Brak seansów</p></div>';
        return;
    }

    el.innerHTML = `<table>
    <thead><tr><th>ID</th><th>Film</th><th>Sala</th><th>Termin</th><th>Akcje</th></tr></thead>
    <tbody>${list.map(s => `
      <tr>
        <td style="font-family:var(--font-mono);color:var(--text-muted)">#${s.id}</td>
        <td><strong>${esc(s.movie.title)}</strong></td>
        <td>${esc(s.room.name)}</td>
        <td>${fmtDateShort(s.dateTime)}</td>
        <td><button class="btn-small btn-danger del-screening" data-id="${s.id}">Usuń</button></td>
      </tr>`).join('')}
    </tbody></table>`;
}

document.getElementById('admin-screenings-list').addEventListener('click', async e => {
    const btn = e.target.closest('.del-screening');
    if (!btn) return;
    if (!confirm('Usunąć seans #' + btn.dataset.id + '?')) return;
    await api('/api/screenings/' + btn.dataset.id, { method: 'DELETE' });
    showToast('Seans usunięty');
    loadAdminScreenings();
});

document.getElementById('screening-form').onsubmit = async e => {
    e.preventDefault();
    const movieId = +document.getElementById('sc-movie').value;
    const roomId = +document.getElementById('sc-room').value;
    const dateTime = document.getElementById('sc-date').value;

    const res = await api('/api/screenings', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            movie: { id: movieId },
            room: { id: roomId },
            dateTime: dateTime
        })
    });

    if (res && res.ok) {
        showToast('Seans utworzony');
        e.target.reset();
        loadAdminScreenings();
    } else {
        const msg = res ? await res.text() : 'Błąd';
        showToast(msg, 'error');
    }
};

// ── Admin: Reservations ──
async function loadAdminReservations() {
    const list = await apiJson('/api/reservations');
    const el = document.getElementById('admin-reservations-list');

    if (!list.length) {
        el.innerHTML = '<div class="screenings-empty"><p>Brak rezerwacji</p></div>';
        return;
    }

    el.innerHTML = `<table>
    <thead><tr><th>Nr</th><th>Film</th><th>Sala</th><th>Termin</th><th>Miejsca</th><th>Akcje</th></tr></thead>
    <tbody>${list.map(r => `
      <tr>
        <td style="font-family:var(--font-mono);color:var(--text-muted)">#${r.id}</td>
        <td>${esc(r.screening.movie.title)}</td>
        <td>${esc(r.screening.room.name)}</td>
        <td>${fmtDateShort(r.screening.dateTime)}</td>
        <td>${r.seats.sort((a,b) => a-b).join(', ')}</td>
        <td><button class="btn-small btn-danger del-res" data-id="${r.id}">Anuluj</button></td>
      </tr>`).join('')}
    </tbody></table>`;
}

document.getElementById('admin-reservations-list').addEventListener('click', async e => {
    const btn = e.target.closest('.del-res');
    if (!btn) return;
    if (!confirm('Anulować rezerwację #' + btn.dataset.id + '?')) return;
    await api('/api/reservations/' + btn.dataset.id, { method: 'DELETE' });
    showToast('Rezerwacja anulowana');
    loadAdminReservations();
});

// ── Admin: Logs ──
async function loadAdminLogs() {
    const res = await api('/api/logs');
    const text = res && res.ok ? await res.text() : 'Błąd ładowania logów';
    document.getElementById('logs-pre').textContent = text || 'Brak logów.';
}

// ══════════════════════════════════════════════════
//  INIT
// ══════════════════════════════════════════════════

// Expose functions used in HTML onclick attributes
window.openMovie = openMovie;
window.show = show;

show('repertoire');