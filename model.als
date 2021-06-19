open util/boolean

// Represents the String contents of the TextField GUI element
sig TextFieldContent {
	// String can be empty
	isEmpty: Bool,
	// Represents if the String is numerical
	isNumerical: Bool,
	// String can map to a number
	number: lone Int,
} {
	// Empty cannot be numerical
	isTrue[isEmpty] => !isTrue[isNumerical]
	// Numerical means there is a number
	isTrue[isNumerical] => number != none
	// Not numerical means there is no number
	!isTrue[isNumerical] => number = none
}

// Represents the actual TextField GUI element
// Note that application uses modified version where only numerical characters allowed
sig TextField {
	content: TextFieldContent,
} {
	// TextField property only allows numerical input (can still be emtpy)
	// TextField also enforces the range
	// Note: max reduced to 5 because of bit limit
	content.number != none => lte[content.number, 5] and gt[content.number, 0]
}

// Represents the Window pop-ups
sig Error {}

// Represents the Screen interface/AbstractScreen class
abstract sig Screen {
	// Holds reference to the Controller
	controller: Controller,
}

// Represents the MaxCalorieScreen (simplified)
sig MaxCalorieScreen extends Screen {
	// Always has a text field
	textField: TextField,
	// Can have an error pop up
	error: lone Error,
}

// Represents the Search Screen (simplified version)
sig SearchScreen extends Screen {}

// Represents the Controller from MVC
sig Controller {
	// Controller stores reference to Model
	facade: ModelFacade,
}

// Represents a simplified ModelFacade
sig ModelFacade {
	// Stores the max calorie value
	max: Int,
}

// Models the button event handler in MaxCalorieScreen
// Note that s' represents the screen state after execution of the method
// s' is Screen as the method might result in it transitioning to SearchScreen
pred pressEnter[s: MaxCalorieScreen, s': Screen] {
	// Passes on the textfield contents and self to controller
	controllerSetMaxCalories[s.controller, s'.controller, s.textField.content, s, s']
}

// Models the setMaxCalories() method in Controller
// c represents the before state of the Controller
// c' represents the after state of the Controller
// s represents the before state of the screen that invoked the method
// s' represents the after state of the screen that invoked the method
pred controllerSetMaxCalories[c, c': Controller, t: TextFieldContent, s, s': Screen] {
	// If not numerical, put error on screen
	!isTrue[t.isNumerical] => {
		// Screen shouldn't have changed
		s' in MaxCalorieScreen

		// Screen should have error
		s'.error != none

		// Max should be the same
		c'.facade.max = c.facade.max
	} else {
		(lt[t.number, 1] or gt[t.number, 5]) implies {
			// Screen shouldn't have changed
			s' in MaxCalorieScreen

			// Screen should have error
			s'.error != none

			// Max should be the same
			c'.facade.max = c.facade.max
		} else {
			// ModelFacade should be updated
			modelSetMaxCalories[c.facade, c'.facade, t.number]

			// Screen should be the SearchScreen
			s' in SearchScreen
		}
	}
}

// Models the setMaxCalories() method in ModelFacade
// m represents the before state of the model
// m' represents the after state of the model
// val is the new value to change to
pred modelSetMaxCalories[m, m': ModelFacade, val: Int] {
	// New max should be equal to val
	eq[m'.max, val]
}

// Ensure empty text field causes error pop up and no change in value
assert emptyContentsHasError {
	all s: MaxCalorieScreen, s': Screen | {
		(isTrue[s.textField.content.isEmpty] and pressEnter[s, s']) => {
			s' in MaxCalorieScreen
			s'.error != none
			s'.controller.facade.max = s.controller.facade.max
		}
	}
}

// Ensure non numerical text field causes error pop up and no change in value
assert nonNumericalContentsHasError {
	all s: MaxCalorieScreen, s': Screen | {
		(!isTrue[s.textField.content.isNumerical] and pressEnter[s, s']) => {
			s' in MaxCalorieScreen
			s'.error != none
			s'.controller.facade.max = s.controller.facade.max
		}
	}
}

// Ensure number below 1 causes error pop up and no change in value
assert numberBelowRangeHasError {
	all s: MaxCalorieScreen, s': Screen | {
		(isTrue[s.textField.content.isNumerical] and pressEnter[s, s'] and
		 lt[s.textField.content.number, 1]) => {
			s' in MaxCalorieScreen
			s'.error != none
			s'.controller.facade.max = s.controller.facade.max
		}
	}
}

// Ensure number above max causes error pop up and no change in value
assert numberAboveRangeHasError {
	all s: MaxCalorieScreen, s': Screen | {
		(isTrue[s.textField.content.isNumerical] and pressEnter[s, s'] and
		 gt[s.textField.content.number, 5]) => {
			s' in MaxCalorieScreen
			s'.error != none
			s'.controller.facade.max = s.controller.facade.max
		}
	}
}

// Ensure number within range causes change in value and screen transition
assert validContentsResultInChange {
	all s: MaxCalorieScreen, s': Screen | {
		(isTrue[s.textField.content.isNumerical] and pressEnter[s, s']) => {
			s' in SearchScreen
			s'.error = none
			eq[s'.controller.facade.max, s.textField.content.number]
		}
	}
}

check emptyContentsHasError
check nonNumericalContentsHasError
check numberBelowRangeHasError
check numberAboveRangeHasError
check validContentsResultInChange

// Check there are valid paths
pred shouldWorkValidRange {
	one s: MaxCalorieScreen, s': SearchScreen | pressEnter[s, s']
}

// Check there are invalid paths
pred shouldWorkInvalidRange {
	one s, s': MaxCalorieScreen | pressEnter[s, s']
}

run shouldWorkValidRange
run shouldWorkInvalidRange