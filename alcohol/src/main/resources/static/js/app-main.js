// alcohol/main 에서만 로드
document.addEventListener('DOMContentLoaded', () => {
  const grid = document.querySelector('body.app .card-grid');
  if (!grid) return; // 메인 아니면 즉시 종료

  // 포커스 가능하게
  grid.querySelectorAll('.card').forEach(card => {
    if (!card.hasAttribute('tabindex')) card.setAttribute('tabindex', '0');
  });

  const pop = (card) => {
    card.classList.remove('pop'); // 재실행용 리셋
    void card.offsetWidth;        // 강제 리플로우
    card.classList.add('pop');
    setTimeout(() => card.classList.remove('pop'), 320);
  };

  // 클릭/키보드 이벤트 위임 (리스너 1개)
  grid.addEventListener('click', (e) => {
    const card = e.target.closest('.card');
    if (!card) return;
    if (e.target.closest('a,button')) return;
    pop(card);
  });
  grid.addEventListener('keydown', (e) => {
    if (e.key !== 'Enter' && e.key !== ' ') return;
    const card = document.activeElement?.closest?.('.card');
    if (!card) return;
    e.preventDefault();
    pop(card);
  });
});
