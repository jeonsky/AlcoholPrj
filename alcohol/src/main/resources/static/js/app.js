document.addEventListener('DOMContentLoaded', () => {
  const $ = (sel, root = document) => root.querySelector(sel);
  const $$ = (sel, root = document) => Array.from(root.querySelectorAll(sel));

  // --- 패널 equal-height (index의 .two 섹션이 있을 때만) ---
  const sections = $$('.two');
  const hasPanels = sections.length > 0;
  const equalize = (section) => {
    const cards = $$('.panel', section);
    if (!cards.length) return;
    // 자연 높이로 초기화 후 최대값 계산
    cards.forEach(c => c.style.height = 'auto');
    const max = Math.max(...cards.map(c => Math.ceil(c.getBoundingClientRect().height)));
    cards.forEach(c => c.style.height = max + 'px');
  };
  const equalizeAll = () => sections.forEach(equalize);

  if (hasPanels) {
    // 폰트 로딩, 이미지 로딩, 리사이즈에 맞춰 재계산
    equalizeAll();
    window.addEventListener('load', equalizeAll);
    if (document.fonts?.ready) document.fonts.ready.then(equalizeAll);

    // ResizeObserver 지원 시 더 정확하게
    if ('ResizeObserver' in window) {
      const ro = new ResizeObserver(() => equalizeAll());
      sections.forEach(sec => ro.observe(sec));
    } else {
      // 폴백: 디바운스 리사이즈
      let t;
      window.addEventListener('resize', () => {
        clearTimeout(t);
        t = setTimeout(equalizeAll, 120);
      });
    }
  }

  // --- 모달 (패널 클릭 시) ---
  // index의 패널이 없으면 모달 로직도 스킵
  if (!hasPanels) return;

  let modal = $('.modal');
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

  const backdrop = $('.modal__backdrop', modal);
  const content  = $('.modal__content', modal);
  const closeBtn = $('.modal__close', modal);
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
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape' && modal.classList.contains('is-open')) {
      e.preventDefault(); closeModal();
    }
  });

  const buildModalHTML = (panel) => {
    const title  = panel.querySelector('h3')?.textContent?.trim() || '';
    const useTeaser = panel.dataset.modal === 'teaser'; // 기술스택 같은 예외
    const teaser = panel.querySelector('.teaser');
    const more   = panel.querySelector('.more');

    let bodyHTML;
    if (useTeaser && teaser) {
      bodyHTML = teaser.innerHTML;
    } else if (more) {
      bodyHTML = more.innerHTML;
    } else {
      const clone = panel.cloneNode(true);
      clone.querySelector('h3')?.remove();
      clone.querySelector('.cta')?.remove();
      bodyHTML = clone.innerHTML;
    }
    return `${title ? `<h3 style="margin:0 0 8px">${title}</h3>` : ''}<div>${bodyHTML}</div>`;
  };

  // 이벤트 위임: 패널 클릭/키보드로 열기
  document.addEventListener('click', (e) => {
    const panel = e.target.closest('.two .panel');
    if (!panel) return;
    if (panel.matches('.no-modal,[data-modal="off"]')) return;
    if (e.target.closest('a,button')) return;
    e.preventDefault();
    openModal(buildModalHTML(panel));
  });
  document.addEventListener('keydown', (e) => {
    if (e.key !== 'Enter' && e.key !== ' ') return;
    const panel = document.activeElement?.closest?.('.two .panel');
    if (!panel || panel.matches('.no-modal,[data-modal="off"]')) return;
    e.preventDefault();
    openModal(buildModalHTML(panel));
  });
});
