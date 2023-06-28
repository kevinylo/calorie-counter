## architecture
Various modules
### app
- Thin layer of module primary for dagger injection as well as manifest in this case
- System wide initialization for libraries could also be in hereCancel changes
### mrp/mvi (core-basemrp)
- Model Renderer Presenter is a Model-View-Intent based paradigm focusing on states/intents and its unidirectional between presenter and the view
- Activity implements the renderer interface (for view initiated actions such as when user intends to modify the current calorie entry)
- Presenter emits states for activity to render on the ui level (states are just simple data classes with the same sealed interface)
- To assist with testing, code generation should be used to generate renderer test class which basically turned all the observable properties into behavior subjects (the code generation code is not included in this project)
### core-interface
- Contains interfaces for the manager, repository as well as a base class (handling scoping)
### repository
- If taking assignment instructions at face value there is really no need to have repository, so the assumptions here is the input json file is gonna be ingested down the road via the network.
- Input can be refreshed by user pulling down the drivers list
- There could also be some data persistence here as well depending on the need
### calories manager
- Depends on the repository and hides the repository related logic from the presenter
### model & model-constant
- Data classes used interally (as opposed to used by persistence layer or network layer)
### model-persistence
- Data classes directly tied to persistence
### calories
- (update) Using Jetpack Compose composeView instead of recyclerView (removing adapter)
- CaloriesActivity (main) controls CaloriesFragment and DetailsFragment
- EntryActivity handles data entry, delete, and modification.
- Contains bits needed for this screen/screens (sub dagger component, states used by presenter, renderer interface, presenter, activity, etc)
- Each screen could be its own module even though they are in one single module now.
### dagger
- App module provides general system resources to be injected
- CaloriesModule provides calories manager, calories repository, food entry database and dao.
