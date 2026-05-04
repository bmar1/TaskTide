# Design Notes

TaskTide uses a calm blue and teal visual system inspired by the wave logo in `images/logobl.png`.

## Visual System

- Primary blue: used for dashboard cards, navigation, and major actions.
- Teal accent: used for the wave brand, secondary emphasis, and progress.
- Light blue surfaces: used for page backgrounds and low-emphasis panels.
- White cards: used for readability and contrast.

## UI Principles

- Keep click targets at least 44px tall where possible.
- Use strong contrast for text on card backgrounds.
- Keep animations and hover feedback subtle because this is a productivity app.
- Prefer consistent spacing over dense fixed-position layouts.
- Keep image assets meaningful: logos for branding, rings for stat cards, trash icons for delete actions.

## Implementation Notes

- Shared colors, fonts, icons, and Swing helpers live in `src/view/TaskTideTheme.java`.
- Task dates are parsed and displayed through `src/model/TaskDateUtils.java`.
- Dashboard actions route through `MainController` so add/delete behavior stays consistent across screens.
- Reminder timers are owned by `MainController`; task cards should not create timers themselves.
