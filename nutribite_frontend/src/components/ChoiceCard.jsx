export default function ChoiceCard({ selected, icon: Icon, title, text, onClick }) {
  return (
    <button type="button" className={`choice ${selected ? "choice--selected" : ""}`} onClick={onClick}>
      <span className="choice-icon"><Icon size={21}/></span>
      <span className="choice-copy"><b>{title}</b><small>{text}</small></span>
      <span className="choice-check">{selected ? "✓" : ""}</span>
    </button>
  );
}
