const fmt = (v) => v == null || Number.isNaN(Number(v)) ? "—" : Number(v).toLocaleString("en-IN", { maximumFractionDigits: 1 });

export default function NutritionCard({ nutrition }) {
  const rows = [
    ["Protein", nutrition?.protein],
    ["Carbs", nutrition?.carbs],
    ["Fat", nutrition?.fat],
    ["Fibre", nutrition?.fiber ?? nutrition?.fibre],
  ];

  return (
    <article className="calorie-card">
      <div className="card-glow" />
      <div className="calorie-top">
        <span><span className="tiny-flame">✦</span> DAILY TARGET</span>
        <span className="calorie-source">{nutrition?.source || "PERSONALIZED"}</span>
      </div>
      <div className="calorie-number">
        <strong>{fmt(nutrition?.dailyCalories)}</strong>
        <span>kcal / day</span>
      </div>
      <p>Your daily energy target from NutriBiteAI. It stays aligned with your profile and backend nutrition plan.</p>
      <div className="macro-grid">
        {rows.map(([name, value]) => (
          <div className="macro" key={name}>
            <div><span>{name}</span><b>{value == null ? "—" : `${fmt(value)} g`}</b></div>
            <i><em style={{ width: value == null ? "0%" : `${Math.min(100, Math.max(8, Number(value) / 2))}%` }} /></i>
          </div>
        ))}
      </div>
    </article>
  );
}
