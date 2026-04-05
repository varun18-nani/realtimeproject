# Design System Document: The Scholarly Path

## 1. Overview & Creative North Star: "The Guided Luminary"
This design system moves away from the cold, rigid structures of traditional academic software. Our Creative North Star is **"The Guided Luminary."** We treat the student’s career journey not as a series of forms to fill, but as an illuminated path. 

To achieve this high-end editorial feel, we break the "template" look by utilizing **intentional asymmetry** (e.g., oversized headlines paired with compact body text) and **tonal layering**. The experience should feel like a premium digital concierge—sophisticated, breathable, and deeply personalized. We favor "white space as a boundary" over lines, and "tonal shifts" over shadows.

---

## 2. Colors: Tonal Depth & Soul
Our palette transitions from the deep, authoritative blues of professional trust to the vibrant, organic greens of personal growth.

### The "No-Line" Rule
**Designers are prohibited from using 1px solid borders to section content.** Boundaries must be defined solely through background color shifts. For example, a `surface-container-low` section should sit directly on a `surface` background to create a soft distinction. 

### Surface Hierarchy & Nesting
Treat the UI as a series of physical layers—like stacked sheets of fine vellum.
- **Base:** `surface` (#f8f9fa)
- **Secondary Content:** `surface-container-low` (#f3f4f5)
- **Primary Cards:** `surface-container-lowest` (#ffffff)
- **Interactive/Floating:** `surface-bright` (#f8f9fa)

### The "Glass & Gradient" Rule
To escape the "flat" look, use **Glassmorphism** for floating navigation or overlay cards. Use `surface-container-lowest` at 80% opacity with a `24px` backdrop-blur. 
**Signature Texture:** For primary CTAs or high-motivation hero sections, use a subtle linear gradient: 
`linear-gradient(135deg, var(--primary) 0%, var(--primary-container) 100%)`.

---

## 3. Typography: Editorial Authority
We utilize a dual-typeface system to balance professional guidance with modern accessibility.

*   **Display & Headlines (Manrope):** Chosen for its geometric precision and modern "tech-forward" feel. Use `display-lg` for high-impact motivational quotes and `headline-md` for section titles.
*   **Body & Titles (Lexend):** Designed specifically to reduce visual noise and improve reading proficiency. This is our "instructional" workhorse.

**Creative Hierarchy Tip:** Use `display-sm` (Manrope) for "The Big Goal" and immediately follow it with `label-md` (Lexend) in `secondary` color for a sophisticated, non-traditional contrast.

---

## 4. Elevation & Depth: Tonal Layering
We reject the standard "Material" drop shadow. Depth is achieved through the **Layering Principle**.

*   **Stacking:** A `surface-container-lowest` card placed on a `surface-container-high` background creates a natural lift.
*   **Ambient Shadows:** If a card must float, use a "Cloud Shadow": `box-shadow: 0 12px 40px rgba(25, 28, 29, 0.06);`. The shadow color must be a tinted version of `on-surface`, never pure black.
*   **The "Ghost Border" Fallback:** If a container requires a border for accessibility, use `outline-variant` at 15% opacity. Never use 100% opaque borders.
*   **Glassmorphism:** Use semi-transparent `surface` containers over `secondary-fixed` decorative elements to create a sense of "frosted growth" in the background.

---

## 5. Components: Fluid & Intentional

### Buttons
*   **Primary:** High-gloss. Gradient from `primary` to `primary-container`. `xl` (1.5rem) roundedness. No border.
*   **Secondary:** Ghost-style. `on-primary-fixed-variant` text on a `surface-container-high` background.
*   **Tertiary:** Text-only with `primary` color, but with a 2px `surface-tint` underline that expands on hover.

### Progress Indicators (Steppers)
*   **The "Organic Trace":** Use a thick (8px) track in `surface-variant`. The active progress should be a gradient of `secondary` to `secondary-container`, with `full` rounded caps. Avoid "beads" on a string; use soft tonal blocks.

### Cards & Lists
*   **Zero-Divider Rule:** Forbid the use of divider lines between list items. Use 16px of vertical white space and a `surface-container-low` hover state to separate content.
*   **The Guidance Card:** A `surface-container-lowest` card with an `xl` corner radius, featuring a `secondary-fixed` accent bar (4px wide) on the left side to denote a "Growth Action."

### Input Fields
*   **Style:** Minimalist. No bottom line. A soft `surface-container-highest` background with `md` roundedness. Focus state moves to a `primary` "Ghost Border" (20% opacity).

---

## 6. Do’s and Don’ts

### Do:
*   **Do** use asymmetrical layouts (e.g., a 60/40 split) to give the app a customized, high-end feel.
*   **Do** use `secondary` (Green) for all "Growth" or "Success" metrics to subconsciously link the app to career evolution.
*   **Do** provide generous breathing room. If it feels "too empty," you’re probably getting close to the right amount of white space.

### Don’t:
*   **Don’t** use pure black (#000000) for text. Always use `on-surface` (#191c1d) to maintain a soft, premium contrast.
*   **Don’t** use standard "Academic Blue." Our blue is `primary` (#2b5ab5)—it’s deeper and more professional.
*   **Don’t** use sharp corners. Everything must feel approachable. Stick to the `lg` (1rem) and `xl` (1.5rem) roundedness scale.