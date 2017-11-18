using UnityEngine;
using System.Collections;

public class AI_ScottSchreiber : MonoBehaviour {

    public CharacterScript mainScript;

    public float[] bombSpeeds;
    public float[] buttonCooldowns;
    public float playerSpeed;
    public int[] beltDirections;
    public float[] buttonLocations;


	//Global variables 
	private float bottomBound = -8f;
	private float topBound = 8f;
	private float bombCharacterBound = 0f;
	private float bombOpponentBound = 18f;
	private int[] intButtonPositions;
	private int intCharacterPosition;
	private int convertedCharacterPosition;
	private int targetButtonIndex;
	private bool enRoute;
	private int previousPosition;
	private float previousHeuristic = 0f;

	private float fullBaseBombTraversalTime = 8f;
	private float slowestCharacterFullTraversalTime = 6f;

	// Use this for initialization
	void Start () {



        mainScript = GetComponent<CharacterScript>();

        if (mainScript == null)
        {
            print("No CharacterScript found on " + gameObject.name);
            this.enabled = false;
        }

        buttonLocations = mainScript.getButtonLocations();

        playerSpeed = mainScript.getPlayerSpeed();


		intButtonPositions = new int[8];
		enRoute = true;

		for (int i = 0; i < mainScript.getButtonLocations ().Length; i++)
			intButtonPositions [i] = (int)mainScript.getButtonLocations () [i];

		convertedCharacterPosition = ConvertCharacterPosition ();
		if (convertedCharacterPosition > 0)
			targetButtonIndex = 0;
		else
			targetButtonIndex = 7;
	}

	// Update is called once per frame
	void Update () {

        buttonCooldowns = mainScript.getButtonCooldowns();
        beltDirections = mainScript.getBeltDirections();


        
        //Your AI code goes here

		/*
			Notes:
			Button positions are between -8.4 and 8.4, with 2.4 between them
			Character position is between -8.58 and 8.58
			For bomb distance, character side is 0, middle is ~9 and opponent side is ~18
			Takes character 6 seconds to fully cross all buttons while pushing every one, 4 seconds without 
			Takes bomb 4 seconds to get from middle of belt to other side at base speed
		*/
		RunAI();


	}

	void RunAI(){
		intCharacterPosition = (int)mainScript.getCharacterLocation ();
		convertedCharacterPosition = ConvertCharacterPosition ();


		//iterate through buttons to find the closest bomb to me that is reachable
		//move to that belt while pressing buttons on the way


		//find next target button
		if (!enRoute) {

			int furthestTargetIndex = 3;
			float heuristic = 0;

			print ("Loop started");
			for (int i = 0; i < mainScript.getButtonLocations ().Length; i++) {


				
				if (mainScript.getBeltDirections () [i] == -1) {
					heuristic = Mathf.Abs (i - convertedCharacterPosition) + mainScript.getBombSpeeds()[i];
					bool canMakeIt = (Mathf.Abs (mainScript.getCharacterLocation () - buttonLocations [i]) / playerSpeed) + 0.35f < mainScript.getBombDistances()[i] / mainScript.getBombSpeeds()[i];
					//if the current belt is further than the previous furthest belt
					if (heuristic > previousHeuristic && canMakeIt) {
						furthestTargetIndex = i;
					}
				}



			}
			targetButtonIndex = furthestTargetIndex;
			print ("Loop ended, new target: " + furthestTargetIndex);
			enRoute = true;
		}




		//move to target button

		if (targetButtonIndex > convertedCharacterPosition)
			mainScript.moveUp ();
		else if (targetButtonIndex < convertedCharacterPosition)
			mainScript.moveDown ();
		else
			enRoute = false;
		
		
			
		//push buttons on the way and at destination
		if (convertedCharacterPosition >-1 && mainScript.getBeltDirections()[convertedCharacterPosition] == -1 || convertedCharacterPosition == targetButtonIndex)
			mainScript.push ();
		//print ("pos: " + mainScript.getBombDistances()[0]);
		//print ("b speed: " + mainScript.getBombSpeeds()[0]);

	

	}

	float GetBombTimeToExplode(int beltIndex){
		
		return 0f;
	}

	/// <summary>
	/// Converts the character's z position into an index from 0-7 that lines up with the buttons
	/// </summary>
	/// <returns>The character position. an index from 0-7</returns>
	int ConvertCharacterPosition(){

		switch (intCharacterPosition) {
		case -8:
			return 0;
		case -6:
			return 1;
		case -3:
			return 2;
		case -1:
			return 3;
		case 1:
			return 4;
		case 3:
			return 5;
		case 6:
			return 6;
		case 8:
			return 7;
		default:
			return convertedCharacterPosition;
		}
	}



}
