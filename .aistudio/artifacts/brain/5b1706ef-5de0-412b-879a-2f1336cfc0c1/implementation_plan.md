# AgriConnect - Agricultural Consultation Platform

AgriConnect is an agricultural guidance platform designed for college demonstration that connects farmers with specialized domain experts. Farmers can describe crop issues, receive tailored expert recommendations, book consultation time slots with simulated payments, and engage in real-time consultation chats with quick-suggestion prompts.

### User Review & Critical Decisions

> [!IMPORTANT]
> The following decisions were confirmed based on the project requirements and college prototype goals:

- **Confirmed Decision 1 (Role Switching)**: One-tap quick switcher between Farmer mode and Expert mode accessible from the top bar and role selector, enabling frictionless evaluation of both perspectives during demo presentations.
- **Confirmed Decision 2 (Data Storage)**: In-memory mock database populated with rich seed data (realistic experts across Agronomy, Plant Pathology, Entomology, Soil Science, etc., pre-existing bookings, and message threads) so that modifications work instantly without requiring complex backend setup or sign-in walls.
- **Confirmed Decision 3 (Consultation Chat)**: Interactive chat interface featuring pre-configured suggestion chips (e.g., "Send leaf photo", "What fertilizer was used?", "Watering frequency?") along with freeform text messaging for rapid demonstration.

---

### 1. Overview & Core Concept

- **What It Does**: Allows farmers to diagnose crop issues by submitting details (crop name, problem category, description, and location), matches them with relevant verified agricultural experts, books time slots with demo payment confirmation, and provides a direct consultation messaging thread. It also includes an Expert Dashboard where specialists can manage their profiles, consultation rates, availability, and view incoming bookings.
- **Target Audience / Persona**:
  - *Farmers*: Looking for quick, practical advice on pests, diseases, soil health, irrigation, and crop yield.
  - *Agricultural Experts / Scientists*: Specialists in Agronomy, Plant Pathology, Entomology, Horticulture, and Soil Science offering guidance.
  - *Academic Reviewers / Evaluators*: Demonstrating an end-to-end multi-role digital agricultural platform workflow.
- **Key Value**: Streamlines the farmer-to-expert consultation lifecycle from initial problem submission to advice delivery in a lightweight, accessible mobile interface.

---

### 2. User Experience & Visual Design

#### Key User Flows

```
[Landing Page]
  │
  ├──► [Ask an Expert] ──► [Submit Problem Form] ──► [Recommended Experts] ──► [Expert Profile & Slots] ──► [Demo Booking & Receipt] ──► [Consultation Chat]
  │
  └──► [Become an Expert / Expert Dashboard] ──► [Edit Profile / Pricing / Hours] ──► [View Active Consultations]
```

1. **Home / Landing View**:
   - Hero banner with lush agricultural theme: "Connect Farmers with the Right Agriculture Experts."
   - Primary action buttons: "Ask an Expert" and "Become an Expert".
   - 3-step feature guide: *Describe your problem* → *Find the right expert* → *Connect with the expert*.
   - Quick Category Explorer (Agronomy, Plant Pathology, Entomology, Soil Science, Horticulture, etc.).

2. **Farmer Experience**:
   - **Ask a Problem**: Form with crop name (e.g., Paddy, Wheat, Cotton, Tomato), category selector (Plant Pathology, Entomology, Agronomy, Soil Science, etc.), problem description, and location.
   - **Expert Matching**: Instant recommendation engine that filters and ranks specialists matching the chosen category.
   - **Expert Details & Booking**: Displays credentials, experience, rating, price (e.g., ₹300), and available time slots. One-tap booking with "Payment Successful — Demo" receipt confirmation.
   - **Consultation Chat**: Live chat view between farmer and expert with quick prompt chips and realistic responses.
   - **My Bookings & Messages**: History of confirmed consultations and open chat threads.

3. **Expert Experience**:
   - **Profile & Schedule Editor**: Edit bio, specialization category, years of experience, consultation fee, and available hours (e.g., Monday–Friday, 5 PM–8 PM).
   - **Booking Requests**: Review scheduled farmer sessions with crop problem summaries.

#### Visual Identity & Theme
- **Theme & Vibe**: Fresh, grounded, natural agriculture aesthetic (emerald and forest greens, crisp white cards, warm amber badges for ratings, soft mint accents).
- **Color Tokens**:
  - Primary: Deep Agricultural Green (`#1E5E3A`)
  - Secondary / Accent: Harvest Gold / Amber (`#F59E0B`)
  - Surface: Crisp White (`#FFFFFF`) with Soft Sage Tint (`#F3F7F4`) for backgrounds
  - Outline & Borders: Subtle earthy leaf border (`#E0EBE2`)
- **Typography**: Clean, readable sans-serif hierarchy with bold headers and comfortable touch targets (48dp+).

---

### 3. Key Product Decisions & Trade-Offs

- **Role Management**:
  - *Chosen Approach*: Unified role switcher at the app top bar with persistent state in `AgriConnectViewModel`, allowing toggling between "Farmer View" and "Expert View" at any moment.
  - *Why*: Eliminates authentication barriers during project grading/demonstrations while maintaining authentic role-specific dashboard views.
- **Category-Based Recommendation Engine**:
  - *Chosen Approach*: Direct category tag mapping linking problem categories (e.g. "Plant Disease" → "Plant Pathology", "Pest Attack" → "Entomology", "Soil Fertility" → "Soil Science").
  - *Why*: Simple, deterministic, and transparent for presentation without requiring black-box algorithms.
- **Demo Booking & Payments**:
  - *Chosen Approach*: In-app simulated payment sheet that immediately confirms the slot and creates a consultation booking with status "Confirmed".
  - *Why*: Complies strictly with the "no real payment gateway" directive while fulfilling the complete transaction workflow.

---

### 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                          AgriConnectApp UI                             │
│                                                                        │
│  ┌───────────────────┐  ┌───────────────────┐  ┌────────────────────┐  │
│  │    HomeScreen     │  │  FarmerDashboard  │  │  ExpertDashboard   │  │
│  └─────────┬─────────┘  └─────────┬─────────┘  └──────────┬─────────┘  │
│            │                      │                       │            │
│            └──────────────┐       │       ┌───────────────┘            │
│                           ▼       ▼       ▼                            │
│                     ┌───────────────────────────┐                      │
│                     │   Booking & Chat Screen   │                      │
│                     └─────────────┬─────────────┘                      │
└───────────────────────────────────┼────────────────────────────────────┘
                                    │ UI Events & State Observation
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│                        AgriConnectViewModel                            │
│   • Current Role (Farmer / Expert)                                     │
│   • Active Navigation Screen & Back Stack                              │
│   • Problem Submission Draft & Filtered Recommendations                │
│   • Active Chat Thread & Instant Replies                               │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │ Repository Operations
                                    ▼
┌────────────────────────────────────────────────────────────────────────┐
│                     AgriConnectMockRepository                          │
│   • Seed Experts (Dr. Rahul Kumar, Dr. Priya Sharma, etc.)             │
│   • Seed Categories (Agronomy, Plant Pathology, Entomology, etc.)      │
│   • Active Consultations & Bookings List                               │
│   • Real-Time Chat Message Store per Consultation                      │
└────────────────────────────────────────────────────────────────────────┘
```

#### Core Data Entities

- `UserRole`: `FARMER`, `EXPERT`
- `ExpertProfile`: `id`, `name`, `specialty`, `category`, `experienceYears`, `rating`, `reviewCount`, `priceInRupees`, `availableSlots`, `location`, `bio`, `avatarColor`
- `AgriculturalCategory`: `id`, `title`, `description`, `iconName`
- `CropProblem`: `id`, `cropName`, `category`, `description`, `location`, `submittedAt`, `status`
- `ConsultationBooking`: `id`, `problemId`, `expertId`, `expertName`, `farmerName`, `cropName`, `dateTimeSlot`, `fee`, `status`
- `ChatMessage`: `id`, `bookingId`, `senderRole`, `message`, `timestamp`

#### Interactive Component Mapping
1. `LandingSection`: Action cards with direct routing to "Ask an Expert" and "Become an Expert".
2. `ProblemSubmissionCard`: Interactive form with validated crop input and category dropdown.
3. `ExpertRecommendationList`: Filtered grid/list of matching specialists with price and rating badges.
4. `BookingModal`: Time slot chips selector with instant "Demo Payment Confirmed" receipt.
5. `ConsultationChatScreen`: Bubble chat with quick-reply diagnosis chips and dynamic message updates.
6. `ExpertManagementScreen`: Slot and price modifier updating the shared repository.
