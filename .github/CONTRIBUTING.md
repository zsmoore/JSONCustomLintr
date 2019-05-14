# Contributing

Any contribution to this repository should first be created as a bug or feature request as an issue, approved, and then merged in via a pull request.

## Branching and Merge policy

Main parts of this repository's branching strategy

* Master branch is to be merged into only for releases
 * Do not commit directly to master branch  
 * Only release branches will be allowed to merge into master
* Dev branch will be used for main development
 * Do not commit directly to dev branch
 * Create PRs to merge feature branches into dev branch
* Create release branches to prep for version release
 * Branch off of dev branch
 * Include changelog in PR
 * Once release is agreed on merge into master and create release in github
* Only rebases allowed
 * Do not have merge commits in PRs
* Always squash commits before merging into dev
 * Each feature branch should only have one commit in the PR before being accepted


## Example Dev Process

* Open Issue for feature
* Branch off of dev
* Commit feature to feature branch
* Finish Feature
* Squash any extra commits into one commit on the feature branch
* Create PR into dev from feature branch, linking issue in pull request
* After 2 reviewers or maintainer approve, rebase feature onto dev

If during dev process changes occur on dev branch, rebase those changes onto your feature branch.


## Pull Request Requirements

* Ensure any build passes, necessary javadocs have been written, all tests pass, and (if applicable) new tests have been written.
* Update the README with any new supported & unsupported cases, or new features.
* You may merge the Pull Request in once you have the sign-off of *two* other developers, the repo owner, or if you
   do not have permission to merge yourself, you may request the second reviewer, or repo owner, to merge it for you.
* Once your Pull Request is merged in, make sure to add documentation to the wiki in order to make sure other developers can look at your code and know what is going on.

## Code of Conduct  

Please read and follow the code of conduct within our base repo directory
