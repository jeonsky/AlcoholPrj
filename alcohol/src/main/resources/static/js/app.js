// app.js — 확장 로직 전부 제거. 모든 패널 '페이지 전체에서 동일 높이'로 통일.
document.addEventListener('DOMContentLoaded', () => {
  document.documentElement.classList.remove('no-js');

  const panels = Array.from(document.querySelectorAll('.two .panel'));
  if (!panels.length) return;

  const equalize = () => {
    // 먼저 자동 높이로 풀고 실제 높이 측정
    panels.forEach(p => { p.style.height = 'auto'; });
    const max = Math.max(...panels.map(p => Math.ceil(p.getBoundingClientRect().height)));
    panels.forEach(p => { p.style.height = max + 'px'; });
  };

  const throttle = (fn, wait=100) => {
    let t = 0; return (...args) => {
      const now = Date.now(); if (now - t < wait) return; t = now; fn(...args);
    };
  };

  equalize();
  window.addEventListener('load', equalize);
  if (document.fonts?.ready) document.fonts.ready.then(equalize);
  window.addEventListener('resize', throttle(equalize, 120));
});
