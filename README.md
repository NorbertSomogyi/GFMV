<h1 align="center" id="title">Generalized Formal Model-Verifier</h1>

<p id="description">The General Formal Model-Verifier is a tool in active research and development with the purpose of formally verifying constraints on static software models. The current implementation is a proof-of-concept aimed at demonstrating the concepts and practical feasibility of the approach. The project was created in Intellij IDEA using kotlin and gradle.</p>

  No pre-built versions are currently available. The verification process is also currently manual. GFMV generates a script that can be used as input for NuSMV. This script contains the definition of the formal model and the formalized constraints, implemented in NuSMV's model definition language. The script was pre-generated and can be found under the output folder.

<h2>How to run</h2>
<p>1. Download and unzip NuSMV for the appropriate platform. 
           
https://nusmv.fbk.eu/downloads.html </p>
<p>2. Download the generated script from the project: output/verify.smv        
<p>3. Navigate to the unzipped NuSMV folder, and copy the downloaded verify.smv script into the bin folder
<p>4. Navigate to NuSMV/bin and run the following command:

```
NuSMV verify.smv
```
By default, NuSMV prints the results of the verification to the console. For each of the 4 constraints, it is either printed as true, or a counter-example is given that corresponds to a violation of that constraint in the formal model.
  
<h2>Structure of the project</h2>
The project consists of 4 parts: the core, launcher, output and uml packages.
<p>1. core: consists of a general API that represents model elements, relations, and generates .smv scripts based on the formal model.

<p>2. launcher: the entry point of the application.

<p>3. output: contains the generated NuSMV script.

<p>4 uml: using the API defined in the core package, manually creates the formal model and the formalized constraints.
