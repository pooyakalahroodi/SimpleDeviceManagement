# GitHub Workflow

This repository should use a protected `main` branch and pull requests for all changes.

## Branch Strategy

- `main` is the stable integration branch.
- Feature work should happen on short-lived branches.
- Use the `dev/` prefix for human-created feature branches.
- Use the `codex/` prefix for Codex-created branches.
- Merge only through pull requests after CI passes.

## Recommended Branch Protection For `main`

In GitHub, open:

```text
Settings -> Branches -> Add branch protection rule
```

Use this branch name pattern:

```text
main
```

Enable:

- Require a pull request before merging.
- Require approvals.
- Require status checks to pass before merging.
- Require branches to be up to date before merging.
- Require conversation resolution before merging.
- Do not allow bypassing the above settings.
- Restrict who can push to matching branches, if available for your GitHub plan.

Required status check:

```text
backend
```

Keep branch protection enabled so direct pushes to `main` stay disabled.

## Daily Workflow

Create a feature branch:

```bash
git checkout main
git pull
git checkout -b dev/name-of-change
```

Make the change, then run the relevant local checks:

```bash
cd SimpleDeviceManagement
mvn clean verify
```

Commit and push the branch:

```bash
git add .
git commit -m "Describe the change"
git push -u origin dev/name-of-change
```

Open a pull request into `main`.

Merge only after:

- The `backend` GitHub Actions check passes.
- Required approvals are complete.
- All review conversations are resolved.
- The branch is up to date with `main`.

## Current CI

The CI pipeline runs on every push and pull request through `.github/workflows/backend.yml`.

The `backend` job currently performs:

```bash
cd SimpleDeviceManagement
mvn -B clean verify
```

This is the appropriate backend check for the current Java/Maven project. If frontend, Python, or browser-extension modules are added later, add separate jobs or extend the workflow with their native checks.

## Pull Request Expectations

Each pull request should include:

- A clear summary of the user-visible or developer-facing change.
- Notes about database, API, or configuration impact.
- Test evidence, including local checks when applicable.
- Screenshots or API examples for user-facing behavior changes.

Avoid mixing unrelated refactors with feature or bug-fix work. Smaller pull requests are easier to review and safer to merge.
