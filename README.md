# Sodium

## Introduction

Sodium is a wrapper around [soda-ash](https://github.com/gadfly361/soda-ash) which,
in turn, is a ClojureScript wrapper around
[semantic-ui-react](https://github.com/Semantic-Org/Semantic-UI-React). Sodium
adds two sets of features into the mix, both aimed at making it easier to include
Semantic UI in re-frame projects:

1. Utility functions to get values in and out of components.
2. Validity-checking aids to help avoid typos.

### Why "Sodium"?

The element sodium is highly reactive. It also starts with an "S", like Soda-ash and
Semantic-UI. In addition, the chemical symbol for sodium is "NA", nicely similar to the
"SA" abbreviation used for the Soda-ash namespace. And, hey, I like puns.

### State of this project

Sodium is still an early work in progress. I am using it to help me with other projects
and have only addded functionality that I need and can test.  Sodium will only grow as
it helps me (or other contributers) accomplish what they need. If you need features now,
PRs are welcome.

### Background

I have used [re-com](https://github.com/Day8/re-com) for a while in my
[re-frame](https://github.com/Day8/re-frame) projects. Re-com is a wonderful, reliable,
easy to use, and opinionated library of UI components. In many ways, it was
perfect. But, as the authors warn, it is designed for desktop applications. When I tried
using it for mobile and portable pages, I kept running into little annoyances.

I recently discovered Semantic UI and its Soda-ash wrapper. I switched to it, and my
pages became much more usable on mobile devices. But, I miss the slick features and
re-frame integration of re-com. Sodium is an attempt to bridge this gap.


## Setup

[![Clojars Project](https://img.shields.io/clojars/v/com.degel/sodium.svg)](https://clojars.org/com.degel/sodium)

Sodium is a wrapper around Soda-ash, Semantic UI React, and Semantic UI. Setup requires
a couple of steps:

- Your project.clj should include a dependency on Sodium: `[com.degel/sodium "0.3.0"]`
- It will probably (see below) also need a dependency on Soda-ash (currently `[soda-ash
  "0.4.0"]`, but see the [Soda-ash README](https://github.com/gadfly361/soda-ash) for
  any recent changes).
- You will also need to include the Semantic UI stylesheet in your _index.html_:
 `<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.12/semantic.min.css">`
- Finally, you will need to require Sodium, and maybe also Soda-ash, in your namespace:
  - `[sodium.core :as na]` - Most of Sodium's functionality
  - `[sodium.utils :refer [<sub >evt]]` - Useful utilities
  - `[sodium.chars :as chars]` - Minor Unicocde support
  - `[soda-ash.core :as sa]` - Soda-ash functions


If you want to contribute to this project, you will want to test your changes. You can
extend the (very minimal) tests inside Sodium, but you will probably need to really test
by using Sodium in a ClojureScript project. The easiest way to do this, while you are
changing Sodium, is by using Leiningen's
[checkouts directory](https://github.com/technomancy/leiningen/blob/master/doc/TUTORIAL.md#checkout-dependencies)
feature by sym-linking directly to your copy of Sodium.

## Using Sodium

Sodium is a library, with a grab-bag of functions to enhance the usability of Semantic
UI from your re-frame projects. You can use it in several ways:

### Data-in and -out

Sodium offers the following:

- `na/>event` and `na/>atom` - Create `:on-*` handlers to pass a value
to a re-frame event or react atom.
- `sodium.core/<atom` - Get a value from a react atom.

#### Typical usage

````
(defn login-panel []
  (let [email (reagent/atom "")
        password (reagent/atom "")]
    (fn []
      [na/form {}
       [na/form-input {:label "Email"
                       :type "email"
                       :on-change (na/>atom email)}]
       [na/form-input {:label "Password"
                       :type "password"
                       :on-change (na/>atom password)}]
       [na/form-button {:content "Login"
                        :on-click (na/>event [:login @email @password])}]])))
````

These functions work equally with with Sodium components and bare Soda-ash ones. I
recommend using Sodium components where available. But, see below, you will still often
need to use Soda-ash components.

### Helper functions

These are some some of the functions that I've needed often in re-frame projects. I
expect this will grow rapidly with time. PRs are welcome here, though I'm likely to be
opinionated about what I accept.

#### In `sodium.utils`
- `validate` - Wrapper for Clojure specs checking in pre-conditions.
- "Camelize" functions - Convert Clojure-style names to JavaScript style

#### In `sodium.re-utils`
- `<sub` and `>evt` - Re-frame wrappers, taken from <https://lambdaisland.com/blog/11-02-2017-re-frame-form-1-subscriptions>

#### In `sodium.chars`

Definitions for a few common Unicode characters.


### Semantic enhancements

HTML controls offer a lot of freedom, and sometimes too many choices. Our front-end code
can be much more readable if we define controls with relatively constrained and
consistent behavior. But, there is an art to doing this right; constrain a control too
much, and it is perfect for one project, but too limited to be useful in other
projects. Semantic UI helps a lot, but still offers too many choices for a consistent,
simple, web page.

I am trying, slowly, to create a set of components that meet my needs yet are general
purpose. Most of them are still in my other projects, mostly not public, that use
Sodium. But, I intend to migrate them into Sodium as I gain comfort.

One component is already here:

- `form-button` - HTML forms are, by default, very tied to the old-school HTML notions
  of page submission. This does not play well with the re-frame philosophy that a form
  handler should simply trigger a re-frame event. When I naively use the Soda-ash
  sa/FormButton component at first, I was hit with unexpected extra connections to my
  server, trying to submit the form. The fix for this, once I realized the problem, was
  simple: the component needs to explicitly speicify a type of "button." The
  `na/form-button` component handles this automatically.

### Type-checked wrappers

This is the most experimental part of Sodium and, sadly, still mostly incomplete.

So far, it includes the following components, each a very thin wrapper around the
corresponding Soda-ash / Semantic UI one:

- `na/checkbox`:   `sa/Checkbox`
- `na/container`:  `sa/Container`
- `na/dropdown`:   `sa/Dropdown`
- `na/form`:        `sa/Form`
- `na/form-input`: `sa/FormInput`
- `na/form-group`: `sa/FormGroup`
- `na/grid`:        `sa/Grid`
- `na/header`:      `sa/Header`
- `na/input`:       `sa/Input`
- `na/menu`:        `sa/Menu`
- `na/menu-item`:  `sa/MenuItem`
- `na/rail`:        `sa/Rail`
- `na/text-area`:  `sa/TextArea`


These wrappers improve the components in two ways (both admittedly arguable):
- The names are in Clojure-friendly kebab-case, rather than camelCase.
- The valid parameters are listed in the arglist. Parameters you supply are
  checked for validity, catching typos and undefind parameters.

These improvements have some drawbacks, which make Sodium still less usable that
Soda-ash in many circumstances. For that reason, I deliberately designed the Sodium
functions to work with both Sodium and Soda-ash components.

Some current limitations:

- I've only defined some components so far; only about a dozen of the 140+ exported by Soda-ash.
- I've tried to list all the valid parameters for each component. I built these lists
  mostly from the Semantic UI React [documentation](https://react.semantic-ui.com/), but
  discovered them to be incomplete. I've added missing parameters as I discovered them,
  but have surely missed some.  [PRs *very* eagerly welcome here].
- The params map is mandatory in Sodium components. It is not valid, e.g., to say
  `[na/form [na/...]]`. Instead, you have to explicitly specify even an empty map:
  `[na/form {} [na/...]]`


### Internals

`sodium.core/defcomponent` and `sodium.core/def-simple-component` Define a component;
typically a thin wrapper around the Soda-ash component, to give parameter checking,
etc. (see below).  Soon, Sodium will contain definitions wrapping all the Soda-ash
(Semantic-UI) components, but we are not there yet.


### Testing

`lein doo phantom test auto` will run the few unit tests I've written so far. More are
needed. PRs especially welcome here.

## Useful references

- https://semantic-ui.com/
- https://react.semantic-ui.com/introduction
- https://github.com/Semantic-Org/Semantic-UI-React
- https://github.com/gadfly361/soda-ashsoda


## Questions

I can usually be found on the [Clojurians Slack](https://clojurians.net) #reagent or
#re-frame slack channels. My handle is @deg. Email is also fine.

## License

Copyright © 2017, David Goldfarb <deg@degel.com>

Licensed under the Eclipse Public License.
