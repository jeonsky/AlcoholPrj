// 메인 전용: 카드 클릭 시 '위로 탁' 애니메이션
document.addEventListener('DOMContentLoaded', () => {
  const cards = Array.from(document.querySelectorAll('body.app .card'));
  cards.forEach(card => {
    card.addEventListener('click', (e) => {
      if (e.target.closest('a,button')) return;
      card.classList.remove('pop');  // 재실행용 리셋
      // 강제 리플로우
      void card.offsetWidth;
      card.classList.add('pop');
      setTimeout(() => card.classList.remove('pop'), 320);
    });
    // 키보드 접근
    if (!card.hasAttribute('tabindex')) card.setAttribute('tabindex','0');
    card.addEventListener('keydown', (e) => {
      if (e.key === 'Enter' || e.key === ' ') { e.preventDefault(); card.click(); }
    });
  });
});
