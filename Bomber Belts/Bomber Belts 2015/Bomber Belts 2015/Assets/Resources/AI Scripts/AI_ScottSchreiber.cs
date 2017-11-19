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
	private int previousTargetIndex;
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
		//sets the initial target for the player
		targetButtonIndex = 3;
		previousTargetIndex = 3;
		/*
		if (convertedCharacterPosition > 0)
			targetButtonIndex = 7;
		else
			targetButtonIndex = 0;
		*/
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
		//find next target button 
		if (!enRoute) {
			//the index of the next target. If no suitable target is found it will default to the center button.
			int furthestTargetIndex = 3;
			/*
			 * heuristic is based on the distance of the button from the player and the speed of the bomb.
			 * Since the player will press buttons on the way to the destination,
			 * the ideal target is the one furthest from the player with the fastest belt speed that is still possible to reach in time.
			*/
			float heuristic = 0;

			print ("Loop started");
			for (int i = 0; i < mainScript.getButtonLocations ().Length; i++) {
				if (mainScript.getBeltDirections () [i] == -1) {
					heuristic = Mathf.Abs (i - convertedCharacterPosition) + mainScript.getBombSpeeds()[i];
					bool reachable = (Mathf.Abs (mainScript.getCharacterLocation () - buttonLocations [i]) / playerSpeed) + 0.35f < mainScript.getBombDistances()[i] / mainScript.getBombSpeeds()[i];
					//if the current belt is a better target than the previous, and is possible to reach in time
					if (heuristic > previousHeuristic && reachable) {
						furthestTargetIndex = i;
					}
				}
			}
			//Code to prevent a tug-of-war situation. If the previous target is the same as the current one, it will target one above or below
			if(previousTargetIndex == furthestTargetIndex)
				if(furthestTargetIndex >= 0 && furthestTargetIndex < 7) //if target is not the top button target one above current target
					targetButtonIndex = furthestTargetIndex + 1;
				else
					targetButtonIndex = furthestTargetIndex - 1; //if target is top button, target one below
			else
				targetButtonIndex = furthestTargetIndex; //target is not the same as previous target
			
			print ("Loop ended, new target: " + furthestTargetIndex);
			enRoute = true;
			previousTargetIndex = targetButtonIndex;
		}

		//move to target button

		if (targetButtonIndex > convertedCharacterPosition)
			mainScript.moveUp ();
		else if (targetButtonIndex < convertedCharacterPosition)
			mainScript.moveDown ();
		else
			enRoute = false;
		
		//push buttons on the way and at destination. Will not press buttons for stationary bombs since they are not yet a danger, and speed is important.
		if (convertedCharacterPosition >-1 && mainScript.getBeltDirections()[convertedCharacterPosition] == -1 || convertedCharacterPosition == targetButtonIndex)
			mainScript.push ();
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
