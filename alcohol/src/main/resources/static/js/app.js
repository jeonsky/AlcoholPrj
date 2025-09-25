// app.js — 패널 클릭 모달(연락 카드 제외)
document.addEventListener('DOMContentLoaded', () => {
  const $all = (sel, root=document) => Array.from(root.querySelectorAll(sel));

  /* 섹션별 동일 높이 */
  const sections = $all('.two');
  const equalize = (section) => {
    const cards = $all('.panel', section);
    if (!cards.length) return;
    cards.forEach(c => c.style.height = 'auto');
    const max = Math.max(...cards.map(c => Math.ceil(c.getBoundingClientRect().height)));
    cards.forEach(c => c.style.height = max + 'px');
  };
  const equalizeAll = () => sections.forEach(equalize);
  equalizeAll();
  window.addEventListener('load', equalizeAll);
  document.fonts?.ready?.then(equalizeAll);
  window.addEventListener('resize', (()=>{let t;return()=>{clearTimeout(t);t=setTimeout(equalizeAll,120);};})());

  /* 모달 생성 */
  let modal = document.querySelector('.modal');
  if (!modal) {
    modal = document.createElement('div');
    modal.className = 'modal';
    modal.innerHTML = `
      <div class="modal__backdrop"></div>
      <div class="modal__dialog" role="dialog" aria-modal="true">
        <div style="display:flex;gap:8px;justify-content:flex-end">
          <button class="modal__close" aria-label="닫기">✕</button>
        </div>
        <div class="modal__content"></div>
      </div>`;
    document.body.appendChild(modal);
  }
  const backdrop = modal.querySelector('.modal__backdrop');
  const content  = modal.querySelector('.modal__content');
  const closeBtn = modal.querySelector('.modal__close');
  let lastFocus = null;

  const openModal = (html) => {
    lastFocus = document.activeElement;
    content.innerHTML = html;
    modal.classList.add('is-open');
    document.body.classList.add('modal-open');
    closeBtn?.focus();
  };
  const closeModal = () => {
    modal.classList.remove('is-open');
    document.body.classList.remove('modal-open');
    content.innerHTML = '';
    lastFocus?.focus?.();
  };
  backdrop?.addEventListener('click', closeModal);
  closeBtn?.addEventListener('click', closeModal);
  document.addEventListener('keydown', e => {
    if (e.key === 'Escape' && modal.classList.contains('is-open')) { e.preventDefault(); closeModal(); }
  });

const buildModalHTML = (panel) => {
  const title  = panel.querySelector('h3')?.textContent?.trim() || '';
  const useTeaser = panel.dataset.modal === 'teaser';   // ⬅️ 예외 처리
  const teaser = panel.querySelector('.teaser');
  const more   = panel.querySelector('.more');

  let bodyHTML;
  if (useTeaser && teaser) {
    // 기술 스택 예외: 카드에 보이던 teaser 전체를 그대로
    bodyHTML = teaser.innerHTML;
  } else if (more) {
    // 기본: 상세(.more)
    bodyHTML = more.innerHTML;
  } else {
    // 폴백: 카드 복제 (제목/CTA 제거)
    const clone = panel.cloneNode(true);
    clone.querySelector('h3')?.remove();
    clone.querySelector('.cta')?.remove();
    bodyHTML = clone.innerHTML;
  }

  return `${title ? `<h3 style="margin:0 0 8px">${title}</h3>` : ''}<div>${bodyHTML}</div>`;
};


  // 이벤트 위임
  document.addEventListener('click', (e) => {
    const panel = e.target.closest('.two .panel');
    if (!panel) return;
    if (e.target.closest('a,button')) return;              // 내부 a/button은 원래 동작
    if (panel.matches('.no-modal,[data-modal="off"]')) return; // 팝업 제외 마크면 무시
    e.preventDefault();
    openModal(buildModalHTML(panel));
  });

  document.addEventListener('keydown', (e) => {
    if (e.key !== 'Enter' && e.key !== ' ') return;
    const panel = document.activeElement?.closest?.('.two .panel');
    if (!panel) return;
    if (panel.matches('.no-modal,[data-modal="off"]')) return;
    e.preventDefault();
    openModal(buildModalHTML(panel));
  });
});
