export default function Logo({ compact = false, className = "" }) {
  return <span className={`brand-logo ${compact ? "brand-logo--compact" : ""} ${className}`.trim()}><img src="/assets/nutribite-logo.png" alt="NutriBiteAI" /></span>;
}
